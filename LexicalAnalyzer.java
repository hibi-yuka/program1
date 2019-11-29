package newlang3;

public interface LexicalAnalyzer { //インタフェースの宣言
    public LexicalUnit get() throws Exception; //LexicalUnit型のget()メソッド
    public boolean expect(LexicalType type) throws Exception; //boolean型のexpect(仮引数はLexicalType型のtype)メソッド
    public void unget(LexicalUnit token) throws Exception; //戻り値なしのunget(LexicalUnit型のtoken)メソッド
}

//また全てのメソッドやコンストラクタを呼び出したい時に、例外処理が発生する事を示している
