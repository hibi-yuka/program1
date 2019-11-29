package newlang3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PushbackReader;


public class Main {



		  public static void main(String[] args)  {

	            String path = "C:\\Users\\c0117312\\Desktop\\test.txt";             //読み込むファイル指定
	            File file = new File(path);                                             //渡されたパスを持つFileインスタンスと、そのインタスタンを指すfile変数作成
	            FileReader fr = null;                                                //ここで一度初期処理
				try {
					fr = new FileReader(file);										//ファイルのパスを指すインタスタンスを指すfileを、指すFileReaderクラスのインスタンスを指す変数fr
				} catch (FileNotFoundException e) {									 //例外処理
					                                                                  // TODO 自動生成された catch ブロック
					e.printStackTrace();
				}                                                                     //ファイル処理された新しいfrリーダ作成
	            PushbackReader pr = new PushbackReader(fr);                           //frを指している、PushbackReaderクラスを指すprを作成
	        	LexicalAnalyzer lu = new LexicalAnalyzerImpl(pr);                    //スーパクラスのLexicalAnalayzer型の変数luが、prを指すLexicalAnalyzerImpl型のインスタンを指す

	        	while(true) {                            //繰り返す
	        		LexicalUnit un = null;              //LexicalUnit型の変数unの初期化を行う
					try {                               //例外処理
						un = lu.get();                  //LexicalAnalyzerImplのgetメソッドより読み込んだ文字をunに渡す
					} catch (Exception e) {			//例外発生時
						                            // TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
	        		System.out.println(un);        //ここでそれぞれの結果を出力する。
	        		if(un.getType() == LexicalType.END) { //LexicalUnitのgetType()より字句出力されている字句がEODなら終わり
	        			break; //true抜ける
	        		}
	        	}
		  }

		}





