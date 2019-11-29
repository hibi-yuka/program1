package newlang3;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {

	PushbackReader word; //フィールド 読み込んだ文字を入れる変数
	int nextword; //結合した文字を入れていく変数
	String input; //最初の一文字を受け取っていく変数


	private static Map<String, LexicalUnit> Reservation = new HashMap<String, LexicalUnit>();//予約語
	private static Map<String, LexicalUnit> operator = new HashMap<String, LexicalUnit>();//演算子,改行
	//Map<キーの型, 値の型> マップ変数 = new HashMap<キーの型, 値の型>(); //総称型の利用（JDK1.5）
	//後ろの方は前の方とと基本的に同じ、例外的にも違う場合もあるけど基本同じ、後ろは省略も可能
	static {
		Reservation.put("IF", new LexicalUnit(LexicalType.IF)); //予約語系
		Reservation.put("THEN", new LexicalUnit(LexicalType.THEN));
		Reservation.put("ELSE", new LexicalUnit(LexicalType.ELSE));
		Reservation.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
		Reservation.put("ENDIF", new LexicalUnit(LexicalType.ENDIF));
		Reservation.put("FOR", new LexicalUnit(LexicalType.FOR));
		Reservation.put("FORALL", new LexicalUnit(LexicalType.FORALL));
		Reservation.put("NEXT", new LexicalUnit(LexicalType.NEXT));
		Reservation.put("SUB", new LexicalUnit(LexicalType.SUB));
		Reservation.put("FUNC", new LexicalUnit(LexicalType.FUNC));
		Reservation.put("DIM", new LexicalUnit(LexicalType.DIM));
		Reservation.put("AS", new LexicalUnit(LexicalType.AS));
		Reservation.put("END", new LexicalUnit(LexicalType.END));
		Reservation.put("DOT", new LexicalUnit(LexicalType.DOT));
		Reservation.put("WHILE", new LexicalUnit(LexicalType.WHILE));
		Reservation.put("DO", new LexicalUnit(LexicalType.DO));
		Reservation.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
		Reservation.put("LOOP", new LexicalUnit(LexicalType.LOOP));
		Reservation.put("TO", new LexicalUnit(LexicalType.TO));
		Reservation.put("WEND", new LexicalUnit(LexicalType.WEND));
		Reservation.put("NL", new LexicalUnit(LexicalType.NL));
		//LexicalUnit i = new LexicalUnit(LexicalType.EQ); //ここは＝で確定なのでvalue一つ
		//operator.put("=",i); 自分に分かる書き方はコレ
		operator.put("=", new LexicalUnit(LexicalType.EQ));
		operator.put("<", new LexicalUnit(LexicalType.LT));
		operator.put(">", new LexicalUnit(LexicalType.GT));
		operator.put("<=", new LexicalUnit(LexicalType.LE));
		operator.put("=<", new LexicalUnit(LexicalType.LE));
		operator.put(">=", new LexicalUnit(LexicalType.GE));
		operator.put("=>", new LexicalUnit(LexicalType.GE));
		operator.put("<>", new LexicalUnit(LexicalType.NE));
		operator.put("+", new LexicalUnit(LexicalType.ADD));
		operator.put("-", new LexicalUnit(LexicalType.SUB));
		operator.put("*", new LexicalUnit(LexicalType.MUL));
		operator.put("/", new LexicalUnit(LexicalType.DIV));
		operator.put(")", new LexicalUnit(LexicalType.LP));
		operator.put("(", new LexicalUnit(LexicalType.RP));
		operator.put(",", new LexicalUnit(LexicalType.COMMA));
		operator.put("\n", new LexicalUnit(LexicalType.NL)); //改行　Unix系OS全般、Mac OS X
		operator.put("\r", new LexicalUnit(LexicalType.NL)); //改行　
		operator.put("\r\n", new LexicalUnit(LexicalType.NL)); //改行　Windows系OS
	}

	public LexicalAnalyzerImpl(PushbackReader pr) { //コンストラクタ、newされたら自動動的に実行されるプログラム。prの情報を受け取る
		this.word = pr;
		;//フィールドに代入
	}


	@Override                                                            //LexicalAnalyzerImplにあるget()を継承
	public LexicalUnit get() throws Exception {                            //get()でどの文字かの判定を開始する。

		                                                                   //コンストラクタで入れた文字を一文字ずつ読みだす
		int inword = word.read();                                        //読み込んだ一文字目を一旦inwordへ
		String first = (char) inword + "";                              //部類分けの為にString型に変換する。


		while (first.equals(" ")||first.equals("\t")) {    // 空白及び、tabキーならば次に←二種類以外が来るまでループし続ける
			inword = word.read();
			first = (char) inword + "";
		}
		if (inword == -1) {                                                 //-1なら終わりなのでEOF
			return new LexicalUnit(LexicalType.EOF);                   //戻り値の指定よりLexicalUnitクラスからインスタンスを作り、EOFを返す
		} else if (first.matches("[a-zA-Z]")) {                      //正規表現でa-zかA-Zなのかを判断する。
			return getName(first);                                //作ったgetNameに返す。これで一文字目が変数か予約語のどちらかではある事になる。
		} else if (first.matches("^\"")) {                         //最初が"なら文字リテラルである
			return getLiteral(first);
		} else if (first.matches("^[0-9]+$")) {                    // 最初が数字なら
			return getNumber(first);
		} else if (operator.containsKey(first)) {                   //予約及び改行含める
			return getOperator(first);
		} else {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
	}



	private LexicalUnit getName(String first) throws IOException {    //getNameはLexcalUnit
		this.input = first;                                             //get()メソッドからの返り値である一文字目がinputnに入る
		while (true) {                                                  //繰り返し
			int letter = word.read();                                  //2(n)文字目を読み込む
			String character = (char) letter + "";                     //""を入れる事でint型からchar型にして、さらにString型変換
			if ((input + character).matches("^[a-zA-Z0-9_]*")) {        ///1文字目と2文字目を結合した文字列を再度判定
				input = input + character;                               //大丈夫な場合は
			} else {
				word.unread(letter);                                       //wordをunreadして,読み込まなかった事にする。ここではまだ処理は終わったわけでない
				if(Reservation.containsKey(input)) {                     //予約語かそうでないかをキーを使って判断する
				 return  Reservation.get(input);//inputとMapのキーの型を比較する事で、予約語かどうか判断する。nullじゃなかったらそのまま返す
				} else {
				LexicalUnit a = new LexicalUnit(LexicalType.NAME ,new ValueImpl(input,ValueType.STRING));       //問題点LexicalUnitクラスのインスタンスを生成し、NAMEをaに渡している
				return a;                                                 //そのLexicalUnitのインスタンスを返す。
				}
			}
		}
	}



	public LexicalUnit getLiteral(String first) throws Exception {       //リテラルの時
  String input = "";
		while (true) {
			nextword = word.read();                                  //"の次の奴
			String n = String.valueOf((char)nextword);             //次に読み込んだ値をString型に変換する事ができる
		//	System.out.println(n);
			if (nextword == -1) {                                    //読み込んだ時に、
				System.err.println("字句解析エラー: ... \"" + n + "\"");
				System.exit(1);   //異常ありという事
			}
			input = input + n;
			if (n.matches("\"")) {                                  //最後に"がマッチしたなら　中に複数のパターンの時
				input = input.substring(0, input.length() - 1);          //""を抜きにした文字列を取り出す 修正 もしくは"は読み込まない
				ValueImpl v = new ValueImpl(input, ValueType.STRING);
				LexicalUnit b = new LexicalUnit(LexicalType.LITERAL, v);
				return b;
				//LexicalUnit b = new LexicalUnit(LexicalType.LITERAL, new ValueImpl(input, ValueType.STRING));
				//LexicalUnit型の変数bはここからの戻り値のTypeはリテラルである事と、文字列と値のtypeがSTRINGであるとする。
			}

		}

	}








	private LexicalUnit getOperator(String first) throws IOException { //改行及び、記号系列　戻り値の型
		this.input = first;
		while (true) {
			nextword = word.read();
			String n = String.valueOf((char) nextword); //二文字目もリテラルへ変換
			if (operator.containsKey(first + n)) {       //一文字目と二文字目を結合し、判定
				first = first + n;                        // /合っていて結果をfirstに代入し更新
			} else {                                     //違う場合は？
				word.unread(nextword);                    //読んだ文字を
				LexicalUnit I = operator.get(first);
				return I;
				//return operator.get(first); 指定されたキーがマップされている値を返す、レキシカルUnit型を返す
			}
		}
	}

	private LexicalUnit getNumber(String first) throws IOException {  //整数及び小数
		this.input = first;
		while (true) {
			nextword = word.read();                       //二文字目
			String m = String.valueOf((char) nextword); //二文字目をStringへ、そしてmに代入
			if (m.matches("[0-9]")) {                    //もし整数なら、小さい実験用コードを書く
				input = input + m;                         //大丈夫ならinputにどんどん代入
			} else if (m.matches("\\.")) {                //mに小数点があるならば
				input = input + m;			               //小数点表記に出来るようにする。
			} else {
				word.unread(nextword);                     //読んだ文字を読まなかった事にする
				break;
			}
		}
		if(input.matches("^[0-9]+$")) { //先頭から末尾まで数字=整数値ならばこちら
		ValueImpl v = new ValueImpl(input, ValueType.INTEGER);
		LexicalUnit c = new LexicalUnit(LexicalType.INTVAL, v);
		return c;

		} else if (input.matches("^[0-9]+\\.[0-9]+$")) { //先頭は数値、途中に小数点、末尾にも数値が来たらこれになる

		ValueImpl i = new ValueImpl(input, ValueType.DOUBLE);
		LexicalUnit d = new LexicalUnit(LexicalType.DOUBLEVAL, i);
		return d;

        }
		//throw Exception("数値の判別に失敗する");//エラーを投げる
		return null;
    }

	@Override
	public boolean expect(LexicalType type) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void unget(LexicalUnit token) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}
}

