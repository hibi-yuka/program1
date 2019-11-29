package newlang3;

public enum LexicalType { //列挙型、staticのクラス変数と似たように、Enum名.値でアクセス
	                       //つまり今回なら LexicalType.各値でアクセス可能
	LITERAL,	// ������萔�@�i��F�@�g������h�j
	INTVAL,		// �����萔	�i��F�@�R�j
	DOUBLEVAL,	// �����_�萔	�i��F�@�P�D�Q�j
	NAME,		// �ϐ�		�i��F�@i�j
	IF ,			// IF
	THEN,		// THEN
	ELSE,		// ELSE
	ELSEIF,		// ELSEIF
	ENDIF,		// ENDIF
	FOR,		// FOR
	FORALL,		// FORALL
	NEXT	,	// NEXT
	EQ,			// =
	LT,			// <
	GT,			// >
	LE,			// <=, =<
	GE,			// >=, =>
	NE,			// <>
	FUNC,		// SUB
	DIM,		// DIM
	AS,			// AS
	END,		// END
	NL,			// ���s
	DOT,		// .
	WHILE,		// WHILE
	DO,			// DO
	UNTIL,		// UNTIL
	ADD,		// +
	SUB,		// -
	MUL,		// *
	DIV,		// /
	LP,			// )
	RP,			// (
	COMMA,		// ,
	LOOP,		// LOOP
	TO,			// TO
	WEND,		// WEND
	EOF,		// end of file
}
