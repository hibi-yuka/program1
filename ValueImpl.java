package newlang3;

public class ValueImpl extends Value{

		String val; //フィールドでString型のval
		ValueType vat;//クラス型であるValueType型のvatは文字列や数値が入る

          public ValueImpl(String s) { //n字句目の文字列を入れたい。
    		super(s);
			 val = s;
		 }

		 public ValueImpl(int i) {
			 super(i);

		 }

		 public ValueImpl(double d) {
			 super(d);

		 }


		 public ValueImpl(boolean b) {
			 super(b);
		 }

		 public ValueImpl(String s, ValueType t) {
			 super(s, t);
			 this.vat = t;
			 this.val = s;

		 }

		 //ここまでvalueから継承したコンストラクタ一覧

		@Override
		public String get_sValue() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public String getSValue() {
			// TODO 自動生成されたメソッド・スタブ
			return this.val;
		}

		@Override
		public int getIValue() {
			// TODO 自動生成されたメソッド・スタブ
			return 0;
		}

		@Override
		public double getDValue() {
			// TODO 自動生成されたメソッド・スタブ
			return 0;
		}

		@Override
		public boolean getBValue() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}

		@Override
		public ValueType getType() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		};

	}






