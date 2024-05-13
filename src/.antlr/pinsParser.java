// Generated from /home/amadej/PINSCompiler/src/pins.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class pinsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, KW_ARR=3, KW_ELSE=4, KW_FOR=5, KW_FUN=6, KW_IF=7, KW_THEN=8, 
		KW_TYP=9, KW_VAR=10, KW_WHERE=11, KW_WHILE=12, KW_DECLARE=13, OP_ADD=14, 
		OP_SUB=15, OP_MUL=16, OP_DIV=17, OP_MOD=18, OP_AND=19, OP_OR=20, OP_NOT=21, 
		OP_EQ=22, OP_NEQ=23, OP_LT=24, OP_GT=25, OP_LEQ=26, OP_GEQ=27, OP_LPARENT=28, 
		OP_RPARENT=29, OP_LBRACKET=30, OP_RBRACKET=31, OP_LBRACE=32, OP_RBRACE=33, 
		OP_COLON=34, OP_SEMICOLON=35, OP_COMMA=36, OP_ASSIGN=37, OP_VARARG=38, 
		C_INTEGER=39, C_LOGICAL=40, C_STRING=41, AT_LOGICAL=42, AT_INTEGER=43, 
		AT_STRING=44, IDENTIFIER=45, COMMENT=46, WS=47;
	public static final int
		RULE_program = 0, RULE_definitions = 1, RULE_definitions2 = 2, RULE_definition = 3, 
		RULE_type_definition = 4, RULE_variable_definition = 5, RULE_function_definition = 6, 
		RULE_function_declaration = 7, RULE_type = 8, RULE_parameters = 9, RULE_parameters2 = 10, 
		RULE_parameter = 11, RULE_expression = 12, RULE_expression2 = 13, RULE_logical_ior_expression = 14, 
		RULE_logical_ior_expression2 = 15, RULE_logical_and_expression = 16, RULE_logical_and_expression2 = 17, 
		RULE_compare_expression = 18, RULE_compare_expression2 = 19, RULE_additive_expression = 20, 
		RULE_additive_expression2 = 21, RULE_multiplicative_expression = 22, RULE_multiplicative_expression2 = 23, 
		RULE_prefix_expression = 24, RULE_postfix_expression = 25, RULE_postfix_expression2 = 26, 
		RULE_atom_expression = 27, RULE_atom_expression2 = 28, RULE_atom_expression3 = 29, 
		RULE_atom_expression4 = 30, RULE_expressions = 31, RULE_expressions2 = 32, 
		RULE_integer = 33, RULE_logical = 34, RULE_string = 35, RULE_int_constant = 36, 
		RULE_log_constant = 37, RULE_str_constant = 38, RULE_identifier = 39, 
		RULE_fun = 40, RULE_typ = 41, RULE_var = 42, RULE_arr = 43, RULE_declare = 44;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "definitions", "definitions2", "definition", "type_definition", 
			"variable_definition", "function_definition", "function_declaration", 
			"type", "parameters", "parameters2", "parameter", "expression", "expression2", 
			"logical_ior_expression", "logical_ior_expression2", "logical_and_expression", 
			"logical_and_expression2", "compare_expression", "compare_expression2", 
			"additive_expression", "additive_expression2", "multiplicative_expression", 
			"multiplicative_expression2", "prefix_expression", "postfix_expression", 
			"postfix_expression2", "atom_expression", "atom_expression2", "atom_expression3", 
			"atom_expression4", "expressions", "expressions2", "integer", "logical", 
			"string", "int_constant", "log_constant", "str_constant", "identifier", 
			"fun", "typ", "var", "arr", "declare"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'WHERE'", "'()'", "'arr'", "'else'", "'for'", "'fun'", "'if'", 
			"'then'", "'typ'", "'var'", "'where'", "'while'", "'declare'", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'&'", "'|'", "'!'", "'=='", "'!='", "'<'", 
			"'>'", "'<='", "'>='", "'('", "')'", "'['", "']'", "'{'", "'}'", "':'", 
			"';'", "','", "'='", "'...'", null, null, null, "'logical'", "'integer'", 
			"'string'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "KW_ARR", "KW_ELSE", "KW_FOR", "KW_FUN", "KW_IF", "KW_THEN", 
			"KW_TYP", "KW_VAR", "KW_WHERE", "KW_WHILE", "KW_DECLARE", "OP_ADD", "OP_SUB", 
			"OP_MUL", "OP_DIV", "OP_MOD", "OP_AND", "OP_OR", "OP_NOT", "OP_EQ", "OP_NEQ", 
			"OP_LT", "OP_GT", "OP_LEQ", "OP_GEQ", "OP_LPARENT", "OP_RPARENT", "OP_LBRACKET", 
			"OP_RBRACKET", "OP_LBRACE", "OP_RBRACE", "OP_COLON", "OP_SEMICOLON", 
			"OP_COMMA", "OP_ASSIGN", "OP_VARARG", "C_INTEGER", "C_LOGICAL", "C_STRING", 
			"AT_LOGICAL", "AT_INTEGER", "AT_STRING", "IDENTIFIER", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "pins.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public pinsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public DefinitionsContext definitions() {
			return getRuleContext(DefinitionsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(pinsParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			definitions();
			setState(91);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefinitionsContext extends ParserRuleContext {
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public Definitions2Context definitions2() {
			return getRuleContext(Definitions2Context.class,0);
		}
		public DefinitionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definitions; }
	}

	public final DefinitionsContext definitions() throws RecognitionException {
		DefinitionsContext _localctx = new DefinitionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_definitions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			definition();
			setState(94);
			definitions2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Definitions2Context extends ParserRuleContext {
		public TerminalNode OP_SEMICOLON() { return getToken(pinsParser.OP_SEMICOLON, 0); }
		public DefinitionsContext definitions() {
			return getRuleContext(DefinitionsContext.class,0);
		}
		public Definitions2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definitions2; }
	}

	public final Definitions2Context definitions2() throws RecognitionException {
		Definitions2Context _localctx = new Definitions2Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_definitions2);
		try {
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_SEMICOLON:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				match(OP_SEMICOLON);
				setState(97);
				definitions();
				}
				break;
			case EOF:
			case OP_RBRACE:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefinitionContext extends ParserRuleContext {
		public Function_definitionContext function_definition() {
			return getRuleContext(Function_definitionContext.class,0);
		}
		public Function_declarationContext function_declaration() {
			return getRuleContext(Function_declarationContext.class,0);
		}
		public Type_definitionContext type_definition() {
			return getRuleContext(Type_definitionContext.class,0);
		}
		public Variable_definitionContext variable_definition() {
			return getRuleContext(Variable_definitionContext.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_definition);
		try {
			setState(105);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KW_FUN:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				function_definition();
				}
				break;
			case KW_DECLARE:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				function_declaration();
				}
				break;
			case KW_TYP:
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				type_definition();
				}
				break;
			case KW_VAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				variable_definition();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_definitionContext extends ParserRuleContext {
		public TypContext typ() {
			return getRuleContext(TypContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Type_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_definition; }
	}

	public final Type_definitionContext type_definition() throws RecognitionException {
		Type_definitionContext _localctx = new Type_definitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			typ();
			setState(108);
			identifier();
			setState(109);
			match(OP_COLON);
			setState(110);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Variable_definitionContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Variable_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_definition; }
	}

	public final Variable_definitionContext variable_definition() throws RecognitionException {
		Variable_definitionContext _localctx = new Variable_definitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variable_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			var();
			setState(113);
			identifier();
			setState(114);
			match(OP_COLON);
			setState(115);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Function_definitionContext extends ParserRuleContext {
		public FunContext fun() {
			return getRuleContext(FunContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OP_LPARENT() { return getToken(pinsParser.OP_LPARENT, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public TerminalNode OP_RPARENT() { return getToken(pinsParser.OP_RPARENT, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OP_ASSIGN() { return getToken(pinsParser.OP_ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			fun();
			setState(118);
			identifier();
			setState(119);
			match(OP_LPARENT);
			setState(120);
			parameters();
			setState(121);
			match(OP_RPARENT);
			setState(122);
			match(OP_COLON);
			setState(123);
			type();
			setState(124);
			match(OP_ASSIGN);
			setState(125);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Function_declarationContext extends ParserRuleContext {
		public DeclareContext declare() {
			return getRuleContext(DeclareContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OP_LPARENT() { return getToken(pinsParser.OP_LPARENT, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public TerminalNode OP_RPARENT() { return getToken(pinsParser.OP_RPARENT, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Function_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_declaration; }
	}

	public final Function_declarationContext function_declaration() throws RecognitionException {
		Function_declarationContext _localctx = new Function_declarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_function_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			declare();
			setState(128);
			identifier();
			setState(129);
			match(OP_LPARENT);
			setState(130);
			parameters();
			setState(131);
			match(OP_RPARENT);
			setState(132);
			match(OP_COLON);
			setState(133);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LogicalContext logical() {
			return getRuleContext(LogicalContext.class,0);
		}
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ArrContext arr() {
			return getRuleContext(ArrContext.class,0);
		}
		public TerminalNode OP_LBRACKET() { return getToken(pinsParser.OP_LBRACKET, 0); }
		public Int_constantContext int_constant() {
			return getRuleContext(Int_constantContext.class,0);
		}
		public TerminalNode OP_RBRACKET() { return getToken(pinsParser.OP_RBRACKET, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		try {
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				identifier();
				}
				break;
			case AT_LOGICAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				logical();
				}
				break;
			case AT_INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(137);
				integer();
				}
				break;
			case AT_STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(138);
				string();
				}
				break;
			case KW_ARR:
				enterOuterAlt(_localctx, 5);
				{
				setState(139);
				arr();
				setState(140);
				match(OP_LBRACKET);
				setState(141);
				int_constant();
				setState(142);
				match(OP_RBRACKET);
				setState(143);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParametersContext extends ParserRuleContext {
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public Parameters2Context parameters2() {
			return getRuleContext(Parameters2Context.class,0);
		}
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_parameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			parameter();
			setState(148);
			parameters2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Parameters2Context extends ParserRuleContext {
		public TerminalNode OP_COMMA() { return getToken(pinsParser.OP_COMMA, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public Parameters2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters2; }
	}

	public final Parameters2Context parameters2() throws RecognitionException {
		Parameters2Context _localctx = new Parameters2Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameters2);
		try {
			setState(153);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_COMMA:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				match(OP_COMMA);
				setState(151);
				parameters();
				}
				break;
			case OP_RPARENT:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OP_VARARG() { return getToken(pinsParser.OP_VARARG, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_parameter);
		try {
			setState(160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				identifier();
				setState(156);
				match(OP_COLON);
				setState(157);
				type();
				}
				break;
			case OP_VARARG:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				match(OP_VARARG);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public Logical_ior_expressionContext logical_ior_expression() {
			return getRuleContext(Logical_ior_expressionContext.class,0);
		}
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			logical_ior_expression();
			setState(163);
			expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression2Context extends ParserRuleContext {
		public TerminalNode OP_LBRACE() { return getToken(pinsParser.OP_LBRACE, 0); }
		public DefinitionsContext definitions() {
			return getRuleContext(DefinitionsContext.class,0);
		}
		public TerminalNode OP_RBRACE() { return getToken(pinsParser.OP_RBRACE, 0); }
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
	}

	public final Expression2Context expression2() throws RecognitionException {
		Expression2Context _localctx = new Expression2Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression2);
		try {
			setState(171);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				match(OP_LBRACE);
				setState(166);
				match(T__0);
				setState(167);
				definitions();
				setState(168);
				match(OP_RBRACE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_ior_expressionContext extends ParserRuleContext {
		public Logical_and_expressionContext logical_and_expression() {
			return getRuleContext(Logical_and_expressionContext.class,0);
		}
		public Logical_ior_expression2Context logical_ior_expression2() {
			return getRuleContext(Logical_ior_expression2Context.class,0);
		}
		public Logical_ior_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_ior_expression; }
	}

	public final Logical_ior_expressionContext logical_ior_expression() throws RecognitionException {
		Logical_ior_expressionContext _localctx = new Logical_ior_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_logical_ior_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			logical_and_expression();
			setState(174);
			logical_ior_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_ior_expression2Context extends ParserRuleContext {
		public TerminalNode OP_OR() { return getToken(pinsParser.OP_OR, 0); }
		public Logical_and_expressionContext logical_and_expression() {
			return getRuleContext(Logical_and_expressionContext.class,0);
		}
		public Logical_ior_expression2Context logical_ior_expression2() {
			return getRuleContext(Logical_ior_expression2Context.class,0);
		}
		public Logical_ior_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_ior_expression2; }
	}

	public final Logical_ior_expression2Context logical_ior_expression2() throws RecognitionException {
		Logical_ior_expression2Context _localctx = new Logical_ior_expression2Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_logical_ior_expression2);
		try {
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				match(OP_OR);
				setState(177);
				logical_and_expression();
				setState(178);
				logical_ior_expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_and_expressionContext extends ParserRuleContext {
		public Compare_expressionContext compare_expression() {
			return getRuleContext(Compare_expressionContext.class,0);
		}
		public Logical_and_expression2Context logical_and_expression2() {
			return getRuleContext(Logical_and_expression2Context.class,0);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logical_and_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			compare_expression();
			setState(184);
			logical_and_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logical_and_expression2Context extends ParserRuleContext {
		public TerminalNode OP_AND() { return getToken(pinsParser.OP_AND, 0); }
		public Compare_expressionContext compare_expression() {
			return getRuleContext(Compare_expressionContext.class,0);
		}
		public Logical_and_expression2Context logical_and_expression2() {
			return getRuleContext(Logical_and_expression2Context.class,0);
		}
		public Logical_and_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression2; }
	}

	public final Logical_and_expression2Context logical_and_expression2() throws RecognitionException {
		Logical_and_expression2Context _localctx = new Logical_and_expression2Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_logical_and_expression2);
		try {
			setState(191);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(186);
				match(OP_AND);
				setState(187);
				compare_expression();
				setState(188);
				logical_and_expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compare_expressionContext extends ParserRuleContext {
		public Additive_expressionContext additive_expression() {
			return getRuleContext(Additive_expressionContext.class,0);
		}
		public Compare_expression2Context compare_expression2() {
			return getRuleContext(Compare_expression2Context.class,0);
		}
		public Compare_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_expression; }
	}

	public final Compare_expressionContext compare_expression() throws RecognitionException {
		Compare_expressionContext _localctx = new Compare_expressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_compare_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			additive_expression();
			setState(194);
			compare_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compare_expression2Context extends ParserRuleContext {
		public TerminalNode OP_EQ() { return getToken(pinsParser.OP_EQ, 0); }
		public Additive_expressionContext additive_expression() {
			return getRuleContext(Additive_expressionContext.class,0);
		}
		public TerminalNode OP_NEQ() { return getToken(pinsParser.OP_NEQ, 0); }
		public TerminalNode OP_LEQ() { return getToken(pinsParser.OP_LEQ, 0); }
		public TerminalNode OP_GEQ() { return getToken(pinsParser.OP_GEQ, 0); }
		public TerminalNode OP_LT() { return getToken(pinsParser.OP_LT, 0); }
		public TerminalNode OP_GT() { return getToken(pinsParser.OP_GT, 0); }
		public Compare_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_expression2; }
	}

	public final Compare_expression2Context compare_expression2() throws RecognitionException {
		Compare_expression2Context _localctx = new Compare_expression2Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_compare_expression2);
		try {
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				match(OP_EQ);
				setState(197);
				additive_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(198);
				match(OP_NEQ);
				setState(199);
				additive_expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(200);
				match(OP_LEQ);
				setState(201);
				additive_expression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(202);
				match(OP_GEQ);
				setState(203);
				additive_expression();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(204);
				match(OP_LT);
				setState(205);
				additive_expression();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(206);
				match(OP_GT);
				setState(207);
				additive_expression();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Additive_expressionContext extends ParserRuleContext {
		public Multiplicative_expressionContext multiplicative_expression() {
			return getRuleContext(Multiplicative_expressionContext.class,0);
		}
		public Additive_expression2Context additive_expression2() {
			return getRuleContext(Additive_expression2Context.class,0);
		}
		public Additive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression; }
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_additive_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			multiplicative_expression();
			setState(212);
			additive_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Additive_expression2Context extends ParserRuleContext {
		public TerminalNode OP_ADD() { return getToken(pinsParser.OP_ADD, 0); }
		public Multiplicative_expressionContext multiplicative_expression() {
			return getRuleContext(Multiplicative_expressionContext.class,0);
		}
		public Additive_expression2Context additive_expression2() {
			return getRuleContext(Additive_expression2Context.class,0);
		}
		public TerminalNode OP_SUB() { return getToken(pinsParser.OP_SUB, 0); }
		public Additive_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression2; }
	}

	public final Additive_expression2Context additive_expression2() throws RecognitionException {
		Additive_expression2Context _localctx = new Additive_expression2Context(_ctx, getState());
		enterRule(_localctx, 42, RULE_additive_expression2);
		try {
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				match(OP_ADD);
				setState(215);
				multiplicative_expression();
				setState(216);
				additive_expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(218);
				match(OP_SUB);
				setState(219);
				multiplicative_expression();
				setState(220);
				additive_expression2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multiplicative_expressionContext extends ParserRuleContext {
		public Prefix_expressionContext prefix_expression() {
			return getRuleContext(Prefix_expressionContext.class,0);
		}
		public Multiplicative_expression2Context multiplicative_expression2() {
			return getRuleContext(Multiplicative_expression2Context.class,0);
		}
		public Multiplicative_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression; }
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_multiplicative_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			prefix_expression();
			setState(226);
			multiplicative_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multiplicative_expression2Context extends ParserRuleContext {
		public TerminalNode OP_MUL() { return getToken(pinsParser.OP_MUL, 0); }
		public Prefix_expressionContext prefix_expression() {
			return getRuleContext(Prefix_expressionContext.class,0);
		}
		public Multiplicative_expression2Context multiplicative_expression2() {
			return getRuleContext(Multiplicative_expression2Context.class,0);
		}
		public TerminalNode OP_DIV() { return getToken(pinsParser.OP_DIV, 0); }
		public TerminalNode OP_MOD() { return getToken(pinsParser.OP_MOD, 0); }
		public Multiplicative_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression2; }
	}

	public final Multiplicative_expression2Context multiplicative_expression2() throws RecognitionException {
		Multiplicative_expression2Context _localctx = new Multiplicative_expression2Context(_ctx, getState());
		enterRule(_localctx, 46, RULE_multiplicative_expression2);
		try {
			setState(241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				match(OP_MUL);
				setState(229);
				prefix_expression();
				setState(230);
				multiplicative_expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(232);
				match(OP_DIV);
				setState(233);
				prefix_expression();
				setState(234);
				multiplicative_expression2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(236);
				match(OP_MOD);
				setState(237);
				prefix_expression();
				setState(238);
				multiplicative_expression2();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Prefix_expressionContext extends ParserRuleContext {
		public TerminalNode OP_ADD() { return getToken(pinsParser.OP_ADD, 0); }
		public Prefix_expressionContext prefix_expression() {
			return getRuleContext(Prefix_expressionContext.class,0);
		}
		public TerminalNode OP_SUB() { return getToken(pinsParser.OP_SUB, 0); }
		public TerminalNode OP_NOT() { return getToken(pinsParser.OP_NOT, 0); }
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Prefix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix_expression; }
	}

	public final Prefix_expressionContext prefix_expression() throws RecognitionException {
		Prefix_expressionContext _localctx = new Prefix_expressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_prefix_expression);
		try {
			setState(250);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(243);
				match(OP_ADD);
				setState(244);
				prefix_expression();
				}
				break;
			case OP_SUB:
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				match(OP_SUB);
				setState(246);
				prefix_expression();
				}
				break;
			case OP_NOT:
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				match(OP_NOT);
				setState(248);
				prefix_expression();
				}
				break;
			case OP_LPARENT:
			case OP_LBRACE:
			case C_INTEGER:
			case C_LOGICAL:
			case C_STRING:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(249);
				postfix_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Postfix_expressionContext extends ParserRuleContext {
		public Atom_expressionContext atom_expression() {
			return getRuleContext(Atom_expressionContext.class,0);
		}
		public Postfix_expression2Context postfix_expression2() {
			return getRuleContext(Postfix_expression2Context.class,0);
		}
		public Postfix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expression; }
	}

	public final Postfix_expressionContext postfix_expression() throws RecognitionException {
		Postfix_expressionContext _localctx = new Postfix_expressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_postfix_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			atom_expression();
			setState(253);
			postfix_expression2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Postfix_expression2Context extends ParserRuleContext {
		public TerminalNode OP_LBRACKET() { return getToken(pinsParser.OP_LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode OP_RBRACKET() { return getToken(pinsParser.OP_RBRACKET, 0); }
		public Postfix_expression2Context postfix_expression2() {
			return getRuleContext(Postfix_expression2Context.class,0);
		}
		public Postfix_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expression2; }
	}

	public final Postfix_expression2Context postfix_expression2() throws RecognitionException {
		Postfix_expression2Context _localctx = new Postfix_expression2Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_postfix_expression2);
		try {
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(255);
				match(OP_LBRACKET);
				setState(256);
				expression();
				setState(257);
				match(OP_RBRACKET);
				setState(258);
				postfix_expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atom_expressionContext extends ParserRuleContext {
		public Log_constantContext log_constant() {
			return getRuleContext(Log_constantContext.class,0);
		}
		public Int_constantContext int_constant() {
			return getRuleContext(Int_constantContext.class,0);
		}
		public Str_constantContext str_constant() {
			return getRuleContext(Str_constantContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public Atom_expression2Context atom_expression2() {
			return getRuleContext(Atom_expression2Context.class,0);
		}
		public TerminalNode OP_LPARENT() { return getToken(pinsParser.OP_LPARENT, 0); }
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public TerminalNode OP_RPARENT() { return getToken(pinsParser.OP_RPARENT, 0); }
		public TerminalNode OP_LBRACE() { return getToken(pinsParser.OP_LBRACE, 0); }
		public Atom_expression4Context atom_expression4() {
			return getRuleContext(Atom_expression4Context.class,0);
		}
		public Atom_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression; }
	}

	public final Atom_expressionContext atom_expression() throws RecognitionException {
		Atom_expressionContext _localctx = new Atom_expressionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_atom_expression);
		try {
			setState(275);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C_LOGICAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				log_constant();
				}
				break;
			case C_INTEGER:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				int_constant();
				}
				break;
			case C_STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				str_constant();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(266);
				identifier();
				setState(267);
				atom_expression2();
				}
				break;
			case OP_LPARENT:
				enterOuterAlt(_localctx, 5);
				{
				setState(269);
				match(OP_LPARENT);
				setState(270);
				expressions();
				setState(271);
				match(OP_RPARENT);
				}
				break;
			case OP_LBRACE:
				enterOuterAlt(_localctx, 6);
				{
				setState(273);
				match(OP_LBRACE);
				setState(274);
				atom_expression4();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atom_expression2Context extends ParserRuleContext {
		public TerminalNode OP_LPARENT() { return getToken(pinsParser.OP_LPARENT, 0); }
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public TerminalNode OP_RPARENT() { return getToken(pinsParser.OP_RPARENT, 0); }
		public Atom_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression2; }
	}

	public final Atom_expression2Context atom_expression2() throws RecognitionException {
		Atom_expression2Context _localctx = new Atom_expression2Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_atom_expression2);
		try {
			setState(283);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				match(T__1);
				}
				break;
			case OP_LPARENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				match(OP_LPARENT);
				setState(279);
				expressions();
				setState(280);
				match(OP_RPARENT);
				}
				break;
			case EOF:
			case KW_ELSE:
			case KW_THEN:
			case OP_ADD:
			case OP_SUB:
			case OP_MUL:
			case OP_DIV:
			case OP_MOD:
			case OP_AND:
			case OP_OR:
			case OP_EQ:
			case OP_NEQ:
			case OP_LT:
			case OP_GT:
			case OP_LEQ:
			case OP_GEQ:
			case OP_RPARENT:
			case OP_LBRACKET:
			case OP_RBRACKET:
			case OP_LBRACE:
			case OP_RBRACE:
			case OP_COLON:
			case OP_SEMICOLON:
			case OP_COMMA:
			case OP_ASSIGN:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atom_expression3Context extends ParserRuleContext {
		public TerminalNode KW_ELSE() { return getToken(pinsParser.KW_ELSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode OP_RBRACE() { return getToken(pinsParser.OP_RBRACE, 0); }
		public Atom_expression3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression3; }
	}

	public final Atom_expression3Context atom_expression3() throws RecognitionException {
		Atom_expression3Context _localctx = new Atom_expression3Context(_ctx, getState());
		enterRule(_localctx, 58, RULE_atom_expression3);
		try {
			setState(290);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(285);
				match(KW_ELSE);
				setState(286);
				expression();
				setState(287);
				match(OP_RBRACE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atom_expression4Context extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OP_ASSIGN() { return getToken(pinsParser.OP_ASSIGN, 0); }
		public TerminalNode OP_RBRACE() { return getToken(pinsParser.OP_RBRACE, 0); }
		public TerminalNode KW_IF() { return getToken(pinsParser.KW_IF, 0); }
		public TerminalNode KW_THEN() { return getToken(pinsParser.KW_THEN, 0); }
		public Atom_expression3Context atom_expression3() {
			return getRuleContext(Atom_expression3Context.class,0);
		}
		public TerminalNode KW_WHILE() { return getToken(pinsParser.KW_WHILE, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TerminalNode KW_FOR() { return getToken(pinsParser.KW_FOR, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<TerminalNode> OP_COMMA() { return getTokens(pinsParser.OP_COMMA); }
		public TerminalNode OP_COMMA(int i) {
			return getToken(pinsParser.OP_COMMA, i);
		}
		public Atom_expression4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression4; }
	}

	public final Atom_expression4Context atom_expression4() throws RecognitionException {
		Atom_expression4Context _localctx = new Atom_expression4Context(_ctx, getState());
		enterRule(_localctx, 60, RULE_atom_expression4);
		try {
			setState(321);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_ADD:
			case OP_SUB:
			case OP_NOT:
			case OP_LPARENT:
			case OP_LBRACE:
			case C_INTEGER:
			case C_LOGICAL:
			case C_STRING:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(292);
				expression();
				setState(293);
				match(OP_ASSIGN);
				setState(294);
				expression();
				setState(295);
				match(OP_RBRACE);
				}
				break;
			case KW_IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				match(KW_IF);
				setState(298);
				expression();
				setState(299);
				match(KW_THEN);
				setState(300);
				expression();
				setState(301);
				atom_expression3();
				}
				break;
			case KW_WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(303);
				match(KW_WHILE);
				setState(304);
				expression();
				setState(305);
				match(OP_COLON);
				setState(306);
				expression();
				setState(307);
				match(OP_RBRACE);
				}
				break;
			case KW_FOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(309);
				match(KW_FOR);
				setState(310);
				identifier();
				setState(311);
				match(OP_ASSIGN);
				setState(312);
				expression();
				setState(313);
				match(OP_COMMA);
				setState(314);
				expression();
				setState(315);
				match(OP_COMMA);
				setState(316);
				expression();
				setState(317);
				match(OP_COLON);
				setState(318);
				expression();
				setState(319);
				match(OP_RBRACE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionsContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expressions2Context expressions2() {
			return getRuleContext(Expressions2Context.class,0);
		}
		public ExpressionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressions; }
	}

	public final ExpressionsContext expressions() throws RecognitionException {
		ExpressionsContext _localctx = new ExpressionsContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_expressions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			expression();
			setState(324);
			expressions2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expressions2Context extends ParserRuleContext {
		public TerminalNode OP_COMMA() { return getToken(pinsParser.OP_COMMA, 0); }
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public Expressions2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressions2; }
	}

	public final Expressions2Context expressions2() throws RecognitionException {
		Expressions2Context _localctx = new Expressions2Context(_ctx, getState());
		enterRule(_localctx, 64, RULE_expressions2);
		try {
			setState(329);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_COMMA:
				enterOuterAlt(_localctx, 1);
				{
				setState(326);
				match(OP_COMMA);
				setState(327);
				expressions();
				}
				break;
			case OP_RPARENT:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode AT_INTEGER() { return getToken(pinsParser.AT_INTEGER, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(AT_INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalContext extends ParserRuleContext {
		public TerminalNode AT_LOGICAL() { return getToken(pinsParser.AT_LOGICAL, 0); }
		public LogicalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical; }
	}

	public final LogicalContext logical() throws RecognitionException {
		LogicalContext _localctx = new LogicalContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_logical);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			match(AT_LOGICAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public TerminalNode AT_STRING() { return getToken(pinsParser.AT_STRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			match(AT_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Int_constantContext extends ParserRuleContext {
		public TerminalNode C_INTEGER() { return getToken(pinsParser.C_INTEGER, 0); }
		public Int_constantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int_constant; }
	}

	public final Int_constantContext int_constant() throws RecognitionException {
		Int_constantContext _localctx = new Int_constantContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_int_constant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(C_INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Log_constantContext extends ParserRuleContext {
		public TerminalNode C_LOGICAL() { return getToken(pinsParser.C_LOGICAL, 0); }
		public Log_constantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_log_constant; }
	}

	public final Log_constantContext log_constant() throws RecognitionException {
		Log_constantContext _localctx = new Log_constantContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_log_constant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(C_LOGICAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Str_constantContext extends ParserRuleContext {
		public TerminalNode C_STRING() { return getToken(pinsParser.C_STRING, 0); }
		public Str_constantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_str_constant; }
	}

	public final Str_constantContext str_constant() throws RecognitionException {
		Str_constantContext _localctx = new Str_constantContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_str_constant);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(C_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunContext extends ParserRuleContext {
		public TerminalNode KW_FUN() { return getToken(pinsParser.KW_FUN, 0); }
		public FunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun; }
	}

	public final FunContext fun() throws RecognitionException {
		FunContext _localctx = new FunContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_fun);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			match(KW_FUN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypContext extends ParserRuleContext {
		public TerminalNode KW_TYP() { return getToken(pinsParser.KW_TYP, 0); }
		public TypContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typ; }
	}

	public final TypContext typ() throws RecognitionException {
		TypContext _localctx = new TypContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_typ);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			match(KW_TYP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends ParserRuleContext {
		public TerminalNode KW_VAR() { return getToken(pinsParser.KW_VAR, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			match(KW_VAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrContext extends ParserRuleContext {
		public TerminalNode KW_ARR() { return getToken(pinsParser.KW_ARR, 0); }
		public ArrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arr; }
	}

	public final ArrContext arr() throws RecognitionException {
		ArrContext _localctx = new ArrContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_arr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(351);
			match(KW_ARR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclareContext extends ParserRuleContext {
		public TerminalNode KW_DECLARE() { return getToken(pinsParser.KW_DECLARE, 0); }
		public DeclareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declare; }
	}

	public final DeclareContext declare() throws RecognitionException {
		DeclareContext _localctx = new DeclareContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_declare);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(KW_DECLARE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001/\u0164\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002d\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003j\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0092\b\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0003\n\u009a\b\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00a1\b\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u00ac\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00b6\b\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u00c0\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0003\u0013\u00d2\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00e0\b\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u00f2\b\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u00fb\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a"+
		"\u0106\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0003\u001b\u0114\b\u001b\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u011c\b\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0123\b\u001d"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e"+
		"\u0142\b\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001"+
		" \u0003 \u014a\b \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001"+
		"$\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0001(\u0001)\u0001"+
		")\u0001*\u0001*\u0001+\u0001+\u0001,\u0001,\u0001,\u0000\u0000-\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.02468:<>@BDFHJLNPRTVX\u0000\u0000\u015e\u0000Z\u0001\u0000"+
		"\u0000\u0000\u0002]\u0001\u0000\u0000\u0000\u0004c\u0001\u0000\u0000\u0000"+
		"\u0006i\u0001\u0000\u0000\u0000\bk\u0001\u0000\u0000\u0000\np\u0001\u0000"+
		"\u0000\u0000\fu\u0001\u0000\u0000\u0000\u000e\u007f\u0001\u0000\u0000"+
		"\u0000\u0010\u0091\u0001\u0000\u0000\u0000\u0012\u0093\u0001\u0000\u0000"+
		"\u0000\u0014\u0099\u0001\u0000\u0000\u0000\u0016\u00a0\u0001\u0000\u0000"+
		"\u0000\u0018\u00a2\u0001\u0000\u0000\u0000\u001a\u00ab\u0001\u0000\u0000"+
		"\u0000\u001c\u00ad\u0001\u0000\u0000\u0000\u001e\u00b5\u0001\u0000\u0000"+
		"\u0000 \u00b7\u0001\u0000\u0000\u0000\"\u00bf\u0001\u0000\u0000\u0000"+
		"$\u00c1\u0001\u0000\u0000\u0000&\u00d1\u0001\u0000\u0000\u0000(\u00d3"+
		"\u0001\u0000\u0000\u0000*\u00df\u0001\u0000\u0000\u0000,\u00e1\u0001\u0000"+
		"\u0000\u0000.\u00f1\u0001\u0000\u0000\u00000\u00fa\u0001\u0000\u0000\u0000"+
		"2\u00fc\u0001\u0000\u0000\u00004\u0105\u0001\u0000\u0000\u00006\u0113"+
		"\u0001\u0000\u0000\u00008\u011b\u0001\u0000\u0000\u0000:\u0122\u0001\u0000"+
		"\u0000\u0000<\u0141\u0001\u0000\u0000\u0000>\u0143\u0001\u0000\u0000\u0000"+
		"@\u0149\u0001\u0000\u0000\u0000B\u014b\u0001\u0000\u0000\u0000D\u014d"+
		"\u0001\u0000\u0000\u0000F\u014f\u0001\u0000\u0000\u0000H\u0151\u0001\u0000"+
		"\u0000\u0000J\u0153\u0001\u0000\u0000\u0000L\u0155\u0001\u0000\u0000\u0000"+
		"N\u0157\u0001\u0000\u0000\u0000P\u0159\u0001\u0000\u0000\u0000R\u015b"+
		"\u0001\u0000\u0000\u0000T\u015d\u0001\u0000\u0000\u0000V\u015f\u0001\u0000"+
		"\u0000\u0000X\u0161\u0001\u0000\u0000\u0000Z[\u0003\u0002\u0001\u0000"+
		"[\\\u0005\u0000\u0000\u0001\\\u0001\u0001\u0000\u0000\u0000]^\u0003\u0006"+
		"\u0003\u0000^_\u0003\u0004\u0002\u0000_\u0003\u0001\u0000\u0000\u0000"+
		"`a\u0005#\u0000\u0000ad\u0003\u0002\u0001\u0000bd\u0001\u0000\u0000\u0000"+
		"c`\u0001\u0000\u0000\u0000cb\u0001\u0000\u0000\u0000d\u0005\u0001\u0000"+
		"\u0000\u0000ej\u0003\f\u0006\u0000fj\u0003\u000e\u0007\u0000gj\u0003\b"+
		"\u0004\u0000hj\u0003\n\u0005\u0000ie\u0001\u0000\u0000\u0000if\u0001\u0000"+
		"\u0000\u0000ig\u0001\u0000\u0000\u0000ih\u0001\u0000\u0000\u0000j\u0007"+
		"\u0001\u0000\u0000\u0000kl\u0003R)\u0000lm\u0003N\'\u0000mn\u0005\"\u0000"+
		"\u0000no\u0003\u0010\b\u0000o\t\u0001\u0000\u0000\u0000pq\u0003T*\u0000"+
		"qr\u0003N\'\u0000rs\u0005\"\u0000\u0000st\u0003\u0010\b\u0000t\u000b\u0001"+
		"\u0000\u0000\u0000uv\u0003P(\u0000vw\u0003N\'\u0000wx\u0005\u001c\u0000"+
		"\u0000xy\u0003\u0012\t\u0000yz\u0005\u001d\u0000\u0000z{\u0005\"\u0000"+
		"\u0000{|\u0003\u0010\b\u0000|}\u0005%\u0000\u0000}~\u0003\u0018\f\u0000"+
		"~\r\u0001\u0000\u0000\u0000\u007f\u0080\u0003X,\u0000\u0080\u0081\u0003"+
		"N\'\u0000\u0081\u0082\u0005\u001c\u0000\u0000\u0082\u0083\u0003\u0012"+
		"\t\u0000\u0083\u0084\u0005\u001d\u0000\u0000\u0084\u0085\u0005\"\u0000"+
		"\u0000\u0085\u0086\u0003\u0010\b\u0000\u0086\u000f\u0001\u0000\u0000\u0000"+
		"\u0087\u0092\u0003N\'\u0000\u0088\u0092\u0003D\"\u0000\u0089\u0092\u0003"+
		"B!\u0000\u008a\u0092\u0003F#\u0000\u008b\u008c\u0003V+\u0000\u008c\u008d"+
		"\u0005\u001e\u0000\u0000\u008d\u008e\u0003H$\u0000\u008e\u008f\u0005\u001f"+
		"\u0000\u0000\u008f\u0090\u0003\u0010\b\u0000\u0090\u0092\u0001\u0000\u0000"+
		"\u0000\u0091\u0087\u0001\u0000\u0000\u0000\u0091\u0088\u0001\u0000\u0000"+
		"\u0000\u0091\u0089\u0001\u0000\u0000\u0000\u0091\u008a\u0001\u0000\u0000"+
		"\u0000\u0091\u008b\u0001\u0000\u0000\u0000\u0092\u0011\u0001\u0000\u0000"+
		"\u0000\u0093\u0094\u0003\u0016\u000b\u0000\u0094\u0095\u0003\u0014\n\u0000"+
		"\u0095\u0013\u0001\u0000\u0000\u0000\u0096\u0097\u0005$\u0000\u0000\u0097"+
		"\u009a\u0003\u0012\t\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099\u0096"+
		"\u0001\u0000\u0000\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\u0015"+
		"\u0001\u0000\u0000\u0000\u009b\u009c\u0003N\'\u0000\u009c\u009d\u0005"+
		"\"\u0000\u0000\u009d\u009e\u0003\u0010\b\u0000\u009e\u00a1\u0001\u0000"+
		"\u0000\u0000\u009f\u00a1\u0005&\u0000\u0000\u00a0\u009b\u0001\u0000\u0000"+
		"\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u0017\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a3\u0003\u001c\u000e\u0000\u00a3\u00a4\u0003\u001a\r\u0000"+
		"\u00a4\u0019\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005 \u0000\u0000\u00a6"+
		"\u00a7\u0005\u0001\u0000\u0000\u00a7\u00a8\u0003\u0002\u0001\u0000\u00a8"+
		"\u00a9\u0005!\u0000\u0000\u00a9\u00ac\u0001\u0000\u0000\u0000\u00aa\u00ac"+
		"\u0001\u0000\u0000\u0000\u00ab\u00a5\u0001\u0000\u0000\u0000\u00ab\u00aa"+
		"\u0001\u0000\u0000\u0000\u00ac\u001b\u0001\u0000\u0000\u0000\u00ad\u00ae"+
		"\u0003 \u0010\u0000\u00ae\u00af\u0003\u001e\u000f\u0000\u00af\u001d\u0001"+
		"\u0000\u0000\u0000\u00b0\u00b1\u0005\u0014\u0000\u0000\u00b1\u00b2\u0003"+
		" \u0010\u0000\u00b2\u00b3\u0003\u001e\u000f\u0000\u00b3\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b4\u00b6\u0001\u0000\u0000\u0000\u00b5\u00b0\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b4\u0001\u0000\u0000\u0000\u00b6\u001f\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0003$\u0012\u0000\u00b8\u00b9\u0003\"\u0011"+
		"\u0000\u00b9!\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005\u0013\u0000\u0000"+
		"\u00bb\u00bc\u0003$\u0012\u0000\u00bc\u00bd\u0003\"\u0011\u0000\u00bd"+
		"\u00c0\u0001\u0000\u0000\u0000\u00be\u00c0\u0001\u0000\u0000\u0000\u00bf"+
		"\u00ba\u0001\u0000\u0000\u0000\u00bf\u00be\u0001\u0000\u0000\u0000\u00c0"+
		"#\u0001\u0000\u0000\u0000\u00c1\u00c2\u0003(\u0014\u0000\u00c2\u00c3\u0003"+
		"&\u0013\u0000\u00c3%\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005\u0016\u0000"+
		"\u0000\u00c5\u00d2\u0003(\u0014\u0000\u00c6\u00c7\u0005\u0017\u0000\u0000"+
		"\u00c7\u00d2\u0003(\u0014\u0000\u00c8\u00c9\u0005\u001a\u0000\u0000\u00c9"+
		"\u00d2\u0003(\u0014\u0000\u00ca\u00cb\u0005\u001b\u0000\u0000\u00cb\u00d2"+
		"\u0003(\u0014\u0000\u00cc\u00cd\u0005\u0018\u0000\u0000\u00cd\u00d2\u0003"+
		"(\u0014\u0000\u00ce\u00cf\u0005\u0019\u0000\u0000\u00cf\u00d2\u0003(\u0014"+
		"\u0000\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00c4\u0001\u0000\u0000"+
		"\u0000\u00d1\u00c6\u0001\u0000\u0000\u0000\u00d1\u00c8\u0001\u0000\u0000"+
		"\u0000\u00d1\u00ca\u0001\u0000\u0000\u0000\u00d1\u00cc\u0001\u0000\u0000"+
		"\u0000\u00d1\u00ce\u0001\u0000\u0000\u0000\u00d1\u00d0\u0001\u0000\u0000"+
		"\u0000\u00d2\'\u0001\u0000\u0000\u0000\u00d3\u00d4\u0003,\u0016\u0000"+
		"\u00d4\u00d5\u0003*\u0015\u0000\u00d5)\u0001\u0000\u0000\u0000\u00d6\u00d7"+
		"\u0005\u000e\u0000\u0000\u00d7\u00d8\u0003,\u0016\u0000\u00d8\u00d9\u0003"+
		"*\u0015\u0000\u00d9\u00e0\u0001\u0000\u0000\u0000\u00da\u00db\u0005\u000f"+
		"\u0000\u0000\u00db\u00dc\u0003,\u0016\u0000\u00dc\u00dd\u0003*\u0015\u0000"+
		"\u00dd\u00e0\u0001\u0000\u0000\u0000\u00de\u00e0\u0001\u0000\u0000\u0000"+
		"\u00df\u00d6\u0001\u0000\u0000\u0000\u00df\u00da\u0001\u0000\u0000\u0000"+
		"\u00df\u00de\u0001\u0000\u0000\u0000\u00e0+\u0001\u0000\u0000\u0000\u00e1"+
		"\u00e2\u00030\u0018\u0000\u00e2\u00e3\u0003.\u0017\u0000\u00e3-\u0001"+
		"\u0000\u0000\u0000\u00e4\u00e5\u0005\u0010\u0000\u0000\u00e5\u00e6\u0003"+
		"0\u0018\u0000\u00e6\u00e7\u0003.\u0017\u0000\u00e7\u00f2\u0001\u0000\u0000"+
		"\u0000\u00e8\u00e9\u0005\u0011\u0000\u0000\u00e9\u00ea\u00030\u0018\u0000"+
		"\u00ea\u00eb\u0003.\u0017\u0000\u00eb\u00f2\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ed\u0005\u0012\u0000\u0000\u00ed\u00ee\u00030\u0018\u0000\u00ee\u00ef"+
		"\u0003.\u0017\u0000\u00ef\u00f2\u0001\u0000\u0000\u0000\u00f0\u00f2\u0001"+
		"\u0000\u0000\u0000\u00f1\u00e4\u0001\u0000\u0000\u0000\u00f1\u00e8\u0001"+
		"\u0000\u0000\u0000\u00f1\u00ec\u0001\u0000\u0000\u0000\u00f1\u00f0\u0001"+
		"\u0000\u0000\u0000\u00f2/\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005\u000e"+
		"\u0000\u0000\u00f4\u00fb\u00030\u0018\u0000\u00f5\u00f6\u0005\u000f\u0000"+
		"\u0000\u00f6\u00fb\u00030\u0018\u0000\u00f7\u00f8\u0005\u0015\u0000\u0000"+
		"\u00f8\u00fb\u00030\u0018\u0000\u00f9\u00fb\u00032\u0019\u0000\u00fa\u00f3"+
		"\u0001\u0000\u0000\u0000\u00fa\u00f5\u0001\u0000\u0000\u0000\u00fa\u00f7"+
		"\u0001\u0000\u0000\u0000\u00fa\u00f9\u0001\u0000\u0000\u0000\u00fb1\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u00036\u001b\u0000\u00fd\u00fe\u00034\u001a"+
		"\u0000\u00fe3\u0001\u0000\u0000\u0000\u00ff\u0100\u0005\u001e\u0000\u0000"+
		"\u0100\u0101\u0003\u0018\f\u0000\u0101\u0102\u0005\u001f\u0000\u0000\u0102"+
		"\u0103\u00034\u001a\u0000\u0103\u0106\u0001\u0000\u0000\u0000\u0104\u0106"+
		"\u0001\u0000\u0000\u0000\u0105\u00ff\u0001\u0000\u0000\u0000\u0105\u0104"+
		"\u0001\u0000\u0000\u0000\u01065\u0001\u0000\u0000\u0000\u0107\u0114\u0003"+
		"J%\u0000\u0108\u0114\u0003H$\u0000\u0109\u0114\u0003L&\u0000\u010a\u010b"+
		"\u0003N\'\u0000\u010b\u010c\u00038\u001c\u0000\u010c\u0114\u0001\u0000"+
		"\u0000\u0000\u010d\u010e\u0005\u001c\u0000\u0000\u010e\u010f\u0003>\u001f"+
		"\u0000\u010f\u0110\u0005\u001d\u0000\u0000\u0110\u0114\u0001\u0000\u0000"+
		"\u0000\u0111\u0112\u0005 \u0000\u0000\u0112\u0114\u0003<\u001e\u0000\u0113"+
		"\u0107\u0001\u0000\u0000\u0000\u0113\u0108\u0001\u0000\u0000\u0000\u0113"+
		"\u0109\u0001\u0000\u0000\u0000\u0113\u010a\u0001\u0000\u0000\u0000\u0113"+
		"\u010d\u0001\u0000\u0000\u0000\u0113\u0111\u0001\u0000\u0000\u0000\u0114"+
		"7\u0001\u0000\u0000\u0000\u0115\u011c\u0005\u0002\u0000\u0000\u0116\u0117"+
		"\u0005\u001c\u0000\u0000\u0117\u0118\u0003>\u001f\u0000\u0118\u0119\u0005"+
		"\u001d\u0000\u0000\u0119\u011c\u0001\u0000\u0000\u0000\u011a\u011c\u0001"+
		"\u0000\u0000\u0000\u011b\u0115\u0001\u0000\u0000\u0000\u011b\u0116\u0001"+
		"\u0000\u0000\u0000\u011b\u011a\u0001\u0000\u0000\u0000\u011c9\u0001\u0000"+
		"\u0000\u0000\u011d\u011e\u0005\u0004\u0000\u0000\u011e\u011f\u0003\u0018"+
		"\f\u0000\u011f\u0120\u0005!\u0000\u0000\u0120\u0123\u0001\u0000\u0000"+
		"\u0000\u0121\u0123\u0001\u0000\u0000\u0000\u0122\u011d\u0001\u0000\u0000"+
		"\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0123;\u0001\u0000\u0000\u0000"+
		"\u0124\u0125\u0003\u0018\f\u0000\u0125\u0126\u0005%\u0000\u0000\u0126"+
		"\u0127\u0003\u0018\f\u0000\u0127\u0128\u0005!\u0000\u0000\u0128\u0142"+
		"\u0001\u0000\u0000\u0000\u0129\u012a\u0005\u0007\u0000\u0000\u012a\u012b"+
		"\u0003\u0018\f\u0000\u012b\u012c\u0005\b\u0000\u0000\u012c\u012d\u0003"+
		"\u0018\f\u0000\u012d\u012e\u0003:\u001d\u0000\u012e\u0142\u0001\u0000"+
		"\u0000\u0000\u012f\u0130\u0005\f\u0000\u0000\u0130\u0131\u0003\u0018\f"+
		"\u0000\u0131\u0132\u0005\"\u0000\u0000\u0132\u0133\u0003\u0018\f\u0000"+
		"\u0133\u0134\u0005!\u0000\u0000\u0134\u0142\u0001\u0000\u0000\u0000\u0135"+
		"\u0136\u0005\u0005\u0000\u0000\u0136\u0137\u0003N\'\u0000\u0137\u0138"+
		"\u0005%\u0000\u0000\u0138\u0139\u0003\u0018\f\u0000\u0139\u013a\u0005"+
		"$\u0000\u0000\u013a\u013b\u0003\u0018\f\u0000\u013b\u013c\u0005$\u0000"+
		"\u0000\u013c\u013d\u0003\u0018\f\u0000\u013d\u013e\u0005\"\u0000\u0000"+
		"\u013e\u013f\u0003\u0018\f\u0000\u013f\u0140\u0005!\u0000\u0000\u0140"+
		"\u0142\u0001\u0000\u0000\u0000\u0141\u0124\u0001\u0000\u0000\u0000\u0141"+
		"\u0129\u0001\u0000\u0000\u0000\u0141\u012f\u0001\u0000\u0000\u0000\u0141"+
		"\u0135\u0001\u0000\u0000\u0000\u0142=\u0001\u0000\u0000\u0000\u0143\u0144"+
		"\u0003\u0018\f\u0000\u0144\u0145\u0003@ \u0000\u0145?\u0001\u0000\u0000"+
		"\u0000\u0146\u0147\u0005$\u0000\u0000\u0147\u014a\u0003>\u001f\u0000\u0148"+
		"\u014a\u0001\u0000\u0000\u0000\u0149\u0146\u0001\u0000\u0000\u0000\u0149"+
		"\u0148\u0001\u0000\u0000\u0000\u014aA\u0001\u0000\u0000\u0000\u014b\u014c"+
		"\u0005+\u0000\u0000\u014cC\u0001\u0000\u0000\u0000\u014d\u014e\u0005*"+
		"\u0000\u0000\u014eE\u0001\u0000\u0000\u0000\u014f\u0150\u0005,\u0000\u0000"+
		"\u0150G\u0001\u0000\u0000\u0000\u0151\u0152\u0005\'\u0000\u0000\u0152"+
		"I\u0001\u0000\u0000\u0000\u0153\u0154\u0005(\u0000\u0000\u0154K\u0001"+
		"\u0000\u0000\u0000\u0155\u0156\u0005)\u0000\u0000\u0156M\u0001\u0000\u0000"+
		"\u0000\u0157\u0158\u0005-\u0000\u0000\u0158O\u0001\u0000\u0000\u0000\u0159"+
		"\u015a\u0005\u0006\u0000\u0000\u015aQ\u0001\u0000\u0000\u0000\u015b\u015c"+
		"\u0005\t\u0000\u0000\u015cS\u0001\u0000\u0000\u0000\u015d\u015e\u0005"+
		"\n\u0000\u0000\u015eU\u0001\u0000\u0000\u0000\u015f\u0160\u0005\u0003"+
		"\u0000\u0000\u0160W\u0001\u0000\u0000\u0000\u0161\u0162\u0005\r\u0000"+
		"\u0000\u0162Y\u0001\u0000\u0000\u0000\u0012ci\u0091\u0099\u00a0\u00ab"+
		"\u00b5\u00bf\u00d1\u00df\u00f1\u00fa\u0105\u0113\u011b\u0122\u0141\u0149";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}