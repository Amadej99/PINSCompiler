// Generated from /home/amadej/PINSCompiler/src/pinsParser.g4 by ANTLR 4.13.1
package compiler.common.antlr;
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
		ARR=1, ELSE=2, FOR=3, FUN=4, IF=5, THEN=6, TYP=7, VAR=8, WHERE=9, WHILE=10, 
		DECLARE=11, OP_ADD=12, OP_SUB=13, OP_MUL=14, OP_DIV=15, OP_MOD=16, OP_AND=17, 
		OP_OR=18, OP_NOT=19, OP_EQ=20, OP_NEQ=21, OP_LT=22, OP_GT=23, OP_LEQ=24, 
		OP_GEQ=25, OP_LPARENT=26, OP_RPARENT=27, OP_LBRACKET=28, OP_RBRACKET=29, 
		OP_LBRACE=30, OP_RBRACE=31, OP_COLON=32, OP_SEMICOLON=33, OP_COMMA=34, 
		OP_ASSIGN=35, OP_VARARG=36, C_INTEGER=37, C_LOGICAL=38, C_STRING=39, AT_LOGICAL=40, 
		AT_INTEGER=41, AT_STRING=42, IDENTIFIER=43, COMMENT=44, WS=45;
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
		RULE_atom_expression4 = 30, RULE_expressions = 31, RULE_expressions2 = 32;
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
			"atom_expression4", "expressions", "expressions2"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'arr'", "'else'", "'for'", "'fun'", "'if'", "'then'", "'typ'", 
			"'var'", "'where'", "'while'", "'declare'", "'+'", "'-'", "'*'", "'/'", 
			"'%'", "'&'", "'|'", "'!'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", 
			"'('", "')'", "'['", "']'", "'{'", "'}'", "':'", "';'", "','", "'='", 
			"'...'", null, null, null, "'logical'", "'integer'", "'string'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ARR", "ELSE", "FOR", "FUN", "IF", "THEN", "TYP", "VAR", "WHERE", 
			"WHILE", "DECLARE", "OP_ADD", "OP_SUB", "OP_MUL", "OP_DIV", "OP_MOD", 
			"OP_AND", "OP_OR", "OP_NOT", "OP_EQ", "OP_NEQ", "OP_LT", "OP_GT", "OP_LEQ", 
			"OP_GEQ", "OP_LPARENT", "OP_RPARENT", "OP_LBRACKET", "OP_RBRACKET", "OP_LBRACE", 
			"OP_RBRACE", "OP_COLON", "OP_SEMICOLON", "OP_COMMA", "OP_ASSIGN", "OP_VARARG", 
			"C_INTEGER", "C_LOGICAL", "C_STRING", "AT_LOGICAL", "AT_INTEGER", "AT_STRING", 
			"IDENTIFIER", "COMMENT", "WS"
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
	public String getGrammarFileName() { return "pinsParser.g4"; }

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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			definitions();
			setState(67);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitDefinitions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionsContext definitions() throws RecognitionException {
		DefinitionsContext _localctx = new DefinitionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_definitions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			definition();
			setState(70);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitDefinitions2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Definitions2Context definitions2() throws RecognitionException {
		Definitions2Context _localctx = new Definitions2Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_definitions2);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_SEMICOLON:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				match(OP_SEMICOLON);
				setState(73);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_definition);
		try {
			setState(81);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUN:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				function_definition();
				}
				break;
			case DECLARE:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				function_declaration();
				}
				break;
			case TYP:
				enterOuterAlt(_localctx, 3);
				{
				setState(79);
				type_definition();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(80);
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
		public TerminalNode TYP() { return getToken(pinsParser.TYP, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Type_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_definition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitType_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_definitionContext type_definition() throws RecognitionException {
		Type_definitionContext _localctx = new Type_definitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(TYP);
			setState(84);
			match(IDENTIFIER);
			setState(85);
			match(OP_COLON);
			setState(86);
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
		public TerminalNode VAR() { return getToken(pinsParser.VAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Variable_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_definition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitVariable_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_definitionContext variable_definition() throws RecognitionException {
		Variable_definitionContext _localctx = new Variable_definitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variable_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(VAR);
			setState(89);
			match(IDENTIFIER);
			setState(90);
			match(OP_COLON);
			setState(91);
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
		public TerminalNode FUN() { return getToken(pinsParser.FUN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitFunction_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(FUN);
			setState(94);
			match(IDENTIFIER);
			setState(95);
			match(OP_LPARENT);
			setState(96);
			parameters();
			setState(97);
			match(OP_RPARENT);
			setState(98);
			match(OP_COLON);
			setState(99);
			type();
			setState(100);
			match(OP_ASSIGN);
			setState(101);
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
		public TerminalNode DECLARE() { return getToken(pinsParser.DECLARE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitFunction_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_declarationContext function_declaration() throws RecognitionException {
		Function_declarationContext _localctx = new Function_declarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_function_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(DECLARE);
			setState(104);
			match(IDENTIFIER);
			setState(105);
			match(OP_LPARENT);
			setState(106);
			parameters();
			setState(107);
			match(OP_RPARENT);
			setState(108);
			match(OP_COLON);
			setState(109);
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
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public TerminalNode AT_LOGICAL() { return getToken(pinsParser.AT_LOGICAL, 0); }
		public TerminalNode AT_INTEGER() { return getToken(pinsParser.AT_INTEGER, 0); }
		public TerminalNode AT_STRING() { return getToken(pinsParser.AT_STRING, 0); }
		public TerminalNode ARR() { return getToken(pinsParser.ARR, 0); }
		public TerminalNode OP_LBRACKET() { return getToken(pinsParser.OP_LBRACKET, 0); }
		public TerminalNode C_INTEGER() { return getToken(pinsParser.C_INTEGER, 0); }
		public TerminalNode OP_RBRACKET() { return getToken(pinsParser.OP_RBRACKET, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		try {
			setState(120);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(111);
				match(IDENTIFIER);
				}
				break;
			case AT_LOGICAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
				match(AT_LOGICAL);
				}
				break;
			case AT_INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(113);
				match(AT_INTEGER);
				}
				break;
			case AT_STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(114);
				match(AT_STRING);
				}
				break;
			case ARR:
				enterOuterAlt(_localctx, 5);
				{
				setState(115);
				match(ARR);
				setState(116);
				match(OP_LBRACKET);
				setState(117);
				match(C_INTEGER);
				setState(118);
				match(OP_RBRACKET);
				setState(119);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_parameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			parameter();
			setState(123);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitParameters2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Parameters2Context parameters2() throws RecognitionException {
		Parameters2Context _localctx = new Parameters2Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameters2);
		try {
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_COMMA:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				match(OP_COMMA);
				setState(126);
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
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OP_VARARG() { return getToken(pinsParser.OP_VARARG, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_parameter);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				match(IDENTIFIER);
				setState(131);
				match(OP_COLON);
				setState(132);
				type();
				}
				break;
			case OP_VARARG:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				match(OP_VARARG);
				}
				break;
			case OP_RPARENT:
			case OP_COMMA:
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			logical_ior_expression();
			setState(138);
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
		public TerminalNode WHERE() { return getToken(pinsParser.WHERE, 0); }
		public DefinitionsContext definitions() {
			return getRuleContext(DefinitionsContext.class,0);
		}
		public TerminalNode OP_RBRACE() { return getToken(pinsParser.OP_RBRACE, 0); }
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitExpression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression2Context expression2() throws RecognitionException {
		Expression2Context _localctx = new Expression2Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression2);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(140);
				match(OP_LBRACE);
				setState(141);
				match(WHERE);
				setState(142);
				definitions();
				setState(143);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitLogical_ior_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_ior_expressionContext logical_ior_expression() throws RecognitionException {
		Logical_ior_expressionContext _localctx = new Logical_ior_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_logical_ior_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			logical_and_expression();
			setState(149);
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
		public Logical_ior_expressionContext logical_ior_expression() {
			return getRuleContext(Logical_ior_expressionContext.class,0);
		}
		public Logical_ior_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_ior_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitLogical_ior_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_ior_expression2Context logical_ior_expression2() throws RecognitionException {
		Logical_ior_expression2Context _localctx = new Logical_ior_expression2Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_logical_ior_expression2);
		try {
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				match(OP_OR);
				setState(152);
				logical_ior_expression();
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitLogical_and_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logical_and_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			compare_expression();
			setState(157);
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
		public Logical_and_expressionContext logical_and_expression() {
			return getRuleContext(Logical_and_expressionContext.class,0);
		}
		public Logical_and_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitLogical_and_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logical_and_expression2Context logical_and_expression2() throws RecognitionException {
		Logical_and_expression2Context _localctx = new Logical_and_expression2Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_logical_and_expression2);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				match(OP_AND);
				setState(160);
				logical_and_expression();
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitCompare_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_expressionContext compare_expression() throws RecognitionException {
		Compare_expressionContext _localctx = new Compare_expressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_compare_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			additive_expression();
			setState(165);
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
		public Token op;
		public Additive_expressionContext additive_expression() {
			return getRuleContext(Additive_expressionContext.class,0);
		}
		public TerminalNode OP_EQ() { return getToken(pinsParser.OP_EQ, 0); }
		public TerminalNode OP_NEQ() { return getToken(pinsParser.OP_NEQ, 0); }
		public TerminalNode OP_LEQ() { return getToken(pinsParser.OP_LEQ, 0); }
		public TerminalNode OP_GEQ() { return getToken(pinsParser.OP_GEQ, 0); }
		public TerminalNode OP_LT() { return getToken(pinsParser.OP_LT, 0); }
		public TerminalNode OP_GT() { return getToken(pinsParser.OP_GT, 0); }
		public Compare_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitCompare_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_expression2Context compare_expression2() throws RecognitionException {
		Compare_expression2Context _localctx = new Compare_expression2Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_compare_expression2);
		int _la;
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(167);
				((Compare_expression2Context)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 66060288L) != 0)) ) {
					((Compare_expression2Context)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(168);
				additive_expression();
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAdditive_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_additive_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			multiplicative_expression();
			setState(173);
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
		public Token op;
		public Additive_expressionContext additive_expression() {
			return getRuleContext(Additive_expressionContext.class,0);
		}
		public TerminalNode OP_ADD() { return getToken(pinsParser.OP_ADD, 0); }
		public TerminalNode OP_SUB() { return getToken(pinsParser.OP_SUB, 0); }
		public Additive_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAdditive_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Additive_expression2Context additive_expression2() throws RecognitionException {
		Additive_expression2Context _localctx = new Additive_expression2Context(_ctx, getState());
		enterRule(_localctx, 42, RULE_additive_expression2);
		int _la;
		try {
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(175);
				((Additive_expression2Context)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==OP_ADD || _la==OP_SUB) ) {
					((Additive_expression2Context)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(176);
				additive_expression();
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitMultiplicative_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_multiplicative_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			prefix_expression();
			setState(181);
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
		public Token op;
		public Multiplicative_expressionContext multiplicative_expression() {
			return getRuleContext(Multiplicative_expressionContext.class,0);
		}
		public TerminalNode OP_MUL() { return getToken(pinsParser.OP_MUL, 0); }
		public TerminalNode OP_DIV() { return getToken(pinsParser.OP_DIV, 0); }
		public TerminalNode OP_MOD() { return getToken(pinsParser.OP_MOD, 0); }
		public Multiplicative_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitMultiplicative_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiplicative_expression2Context multiplicative_expression2() throws RecognitionException {
		Multiplicative_expression2Context _localctx = new Multiplicative_expression2Context(_ctx, getState());
		enterRule(_localctx, 46, RULE_multiplicative_expression2);
		int _la;
		try {
			setState(186);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(183);
				((Multiplicative_expression2Context)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 114688L) != 0)) ) {
					((Multiplicative_expression2Context)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(184);
				multiplicative_expression();
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
	public static class Prefix_expressionContext extends ParserRuleContext {
		public Token op;
		public Prefix_expressionContext prefix_expression() {
			return getRuleContext(Prefix_expressionContext.class,0);
		}
		public TerminalNode OP_ADD() { return getToken(pinsParser.OP_ADD, 0); }
		public TerminalNode OP_SUB() { return getToken(pinsParser.OP_SUB, 0); }
		public TerminalNode OP_NOT() { return getToken(pinsParser.OP_NOT, 0); }
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Prefix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitPrefix_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Prefix_expressionContext prefix_expression() throws RecognitionException {
		Prefix_expressionContext _localctx = new Prefix_expressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_prefix_expression);
		int _la;
		try {
			setState(191);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_ADD:
			case OP_SUB:
			case OP_NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(188);
				((Prefix_expressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 536576L) != 0)) ) {
					((Prefix_expressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(189);
				prefix_expression();
				}
				break;
			case OP_LPARENT:
			case OP_LBRACE:
			case C_INTEGER:
			case C_LOGICAL:
			case C_STRING:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitPostfix_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Postfix_expressionContext postfix_expression() throws RecognitionException {
		Postfix_expressionContext _localctx = new Postfix_expressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_postfix_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			atom_expression();
			setState(194);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitPostfix_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Postfix_expression2Context postfix_expression2() throws RecognitionException {
		Postfix_expression2Context _localctx = new Postfix_expression2Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_postfix_expression2);
		try {
			setState(202);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				match(OP_LBRACKET);
				setState(197);
				expression();
				setState(198);
				match(OP_RBRACKET);
				setState(199);
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
		public TerminalNode C_LOGICAL() { return getToken(pinsParser.C_LOGICAL, 0); }
		public TerminalNode C_INTEGER() { return getToken(pinsParser.C_INTEGER, 0); }
		public TerminalNode C_STRING() { return getToken(pinsParser.C_STRING, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAtom_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_expressionContext atom_expression() throws RecognitionException {
		Atom_expressionContext _localctx = new Atom_expressionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_atom_expression);
		try {
			setState(215);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C_LOGICAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				match(C_LOGICAL);
				}
				break;
			case C_INTEGER:
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				match(C_INTEGER);
				}
				break;
			case C_STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(206);
				match(C_STRING);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(207);
				match(IDENTIFIER);
				setState(208);
				atom_expression2();
				}
				break;
			case OP_LPARENT:
				enterOuterAlt(_localctx, 5);
				{
				setState(209);
				match(OP_LPARENT);
				setState(210);
				expressions();
				setState(211);
				match(OP_RPARENT);
				}
				break;
			case OP_LBRACE:
				enterOuterAlt(_localctx, 6);
				{
				setState(213);
				match(OP_LBRACE);
				setState(214);
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
		public TerminalNode OP_RPARENT() { return getToken(pinsParser.OP_RPARENT, 0); }
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public Atom_expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAtom_expression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_expression2Context atom_expression2() throws RecognitionException {
		Atom_expression2Context _localctx = new Atom_expression2Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_atom_expression2);
		try {
			setState(224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(217);
				match(OP_LPARENT);
				setState(218);
				match(OP_RPARENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(219);
				match(OP_LPARENT);
				setState(220);
				expressions();
				setState(221);
				match(OP_RPARENT);
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
	public static class Atom_expression3Context extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(pinsParser.ELSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode OP_RBRACE() { return getToken(pinsParser.OP_RBRACE, 0); }
		public Atom_expression3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression3; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAtom_expression3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_expression3Context atom_expression3() throws RecognitionException {
		Atom_expression3Context _localctx = new Atom_expression3Context(_ctx, getState());
		enterRule(_localctx, 58, RULE_atom_expression3);
		try {
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(226);
				match(ELSE);
				setState(227);
				expression();
				setState(228);
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
		public TerminalNode IF() { return getToken(pinsParser.IF, 0); }
		public TerminalNode THEN() { return getToken(pinsParser.THEN, 0); }
		public Atom_expression3Context atom_expression3() {
			return getRuleContext(Atom_expression3Context.class,0);
		}
		public TerminalNode WHILE() { return getToken(pinsParser.WHILE, 0); }
		public TerminalNode OP_COLON() { return getToken(pinsParser.OP_COLON, 0); }
		public TerminalNode FOR() { return getToken(pinsParser.FOR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(pinsParser.IDENTIFIER, 0); }
		public List<TerminalNode> OP_COMMA() { return getTokens(pinsParser.OP_COMMA); }
		public TerminalNode OP_COMMA(int i) {
			return getToken(pinsParser.OP_COMMA, i);
		}
		public Atom_expression4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expression4; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitAtom_expression4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_expression4Context atom_expression4() throws RecognitionException {
		Atom_expression4Context _localctx = new Atom_expression4Context(_ctx, getState());
		enterRule(_localctx, 60, RULE_atom_expression4);
		try {
			setState(262);
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
				setState(233);
				expression();
				setState(234);
				match(OP_ASSIGN);
				setState(235);
				expression();
				setState(236);
				match(OP_RBRACE);
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				match(IF);
				setState(239);
				expression();
				setState(240);
				match(THEN);
				setState(241);
				expression();
				setState(242);
				atom_expression3();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(244);
				match(WHILE);
				setState(245);
				expression();
				setState(246);
				match(OP_COLON);
				setState(247);
				expression();
				setState(248);
				match(OP_RBRACE);
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(250);
				match(FOR);
				setState(251);
				match(IDENTIFIER);
				setState(252);
				match(OP_ASSIGN);
				setState(253);
				expression();
				setState(254);
				match(OP_COMMA);
				setState(255);
				expression();
				setState(256);
				match(OP_COMMA);
				setState(257);
				expression();
				setState(258);
				match(OP_COLON);
				setState(259);
				expression();
				setState(260);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitExpressions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionsContext expressions() throws RecognitionException {
		ExpressionsContext _localctx = new ExpressionsContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_expressions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			expression();
			setState(265);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pinsParserVisitor ) return ((pinsParserVisitor<? extends T>)visitor).visitExpressions2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expressions2Context expressions2() throws RecognitionException {
		Expressions2Context _localctx = new Expressions2Context(_ctx, getState());
		enterRule(_localctx, 64, RULE_expressions2);
		try {
			setState(270);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_COMMA:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				match(OP_COMMA);
				setState(268);
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

	public static final String _serializedATN =
		"\u0004\u0001-\u0111\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002L\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003R\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\by\b\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0003"+
		"\n\u0081\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0003\u000b\u0088\b\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0003\r\u0093\b\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u009b\b\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0003"+
		"\u0011\u00a3\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0003\u0013\u00ab\b\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00b3\b\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0003"+
		"\u0017\u00bb\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u00c0"+
		"\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u00cb\b\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u00d8"+
		"\b\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0003\u001c\u00e1\b\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u00e8\b\u001d\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u0107\b\u001e\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0003 \u010f\b \u0001"+
		" \u0000\u0000!\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@\u0000\u0004\u0001\u0000\u0014"+
		"\u0019\u0001\u0000\f\r\u0001\u0000\u000e\u0010\u0002\u0000\f\r\u0013\u0013"+
		"\u010e\u0000B\u0001\u0000\u0000\u0000\u0002E\u0001\u0000\u0000\u0000\u0004"+
		"K\u0001\u0000\u0000\u0000\u0006Q\u0001\u0000\u0000\u0000\bS\u0001\u0000"+
		"\u0000\u0000\nX\u0001\u0000\u0000\u0000\f]\u0001\u0000\u0000\u0000\u000e"+
		"g\u0001\u0000\u0000\u0000\u0010x\u0001\u0000\u0000\u0000\u0012z\u0001"+
		"\u0000\u0000\u0000\u0014\u0080\u0001\u0000\u0000\u0000\u0016\u0087\u0001"+
		"\u0000\u0000\u0000\u0018\u0089\u0001\u0000\u0000\u0000\u001a\u0092\u0001"+
		"\u0000\u0000\u0000\u001c\u0094\u0001\u0000\u0000\u0000\u001e\u009a\u0001"+
		"\u0000\u0000\u0000 \u009c\u0001\u0000\u0000\u0000\"\u00a2\u0001\u0000"+
		"\u0000\u0000$\u00a4\u0001\u0000\u0000\u0000&\u00aa\u0001\u0000\u0000\u0000"+
		"(\u00ac\u0001\u0000\u0000\u0000*\u00b2\u0001\u0000\u0000\u0000,\u00b4"+
		"\u0001\u0000\u0000\u0000.\u00ba\u0001\u0000\u0000\u00000\u00bf\u0001\u0000"+
		"\u0000\u00002\u00c1\u0001\u0000\u0000\u00004\u00ca\u0001\u0000\u0000\u0000"+
		"6\u00d7\u0001\u0000\u0000\u00008\u00e0\u0001\u0000\u0000\u0000:\u00e7"+
		"\u0001\u0000\u0000\u0000<\u0106\u0001\u0000\u0000\u0000>\u0108\u0001\u0000"+
		"\u0000\u0000@\u010e\u0001\u0000\u0000\u0000BC\u0003\u0002\u0001\u0000"+
		"CD\u0005\u0000\u0000\u0001D\u0001\u0001\u0000\u0000\u0000EF\u0003\u0006"+
		"\u0003\u0000FG\u0003\u0004\u0002\u0000G\u0003\u0001\u0000\u0000\u0000"+
		"HI\u0005!\u0000\u0000IL\u0003\u0002\u0001\u0000JL\u0001\u0000\u0000\u0000"+
		"KH\u0001\u0000\u0000\u0000KJ\u0001\u0000\u0000\u0000L\u0005\u0001\u0000"+
		"\u0000\u0000MR\u0003\f\u0006\u0000NR\u0003\u000e\u0007\u0000OR\u0003\b"+
		"\u0004\u0000PR\u0003\n\u0005\u0000QM\u0001\u0000\u0000\u0000QN\u0001\u0000"+
		"\u0000\u0000QO\u0001\u0000\u0000\u0000QP\u0001\u0000\u0000\u0000R\u0007"+
		"\u0001\u0000\u0000\u0000ST\u0005\u0007\u0000\u0000TU\u0005+\u0000\u0000"+
		"UV\u0005 \u0000\u0000VW\u0003\u0010\b\u0000W\t\u0001\u0000\u0000\u0000"+
		"XY\u0005\b\u0000\u0000YZ\u0005+\u0000\u0000Z[\u0005 \u0000\u0000[\\\u0003"+
		"\u0010\b\u0000\\\u000b\u0001\u0000\u0000\u0000]^\u0005\u0004\u0000\u0000"+
		"^_\u0005+\u0000\u0000_`\u0005\u001a\u0000\u0000`a\u0003\u0012\t\u0000"+
		"ab\u0005\u001b\u0000\u0000bc\u0005 \u0000\u0000cd\u0003\u0010\b\u0000"+
		"de\u0005#\u0000\u0000ef\u0003\u0018\f\u0000f\r\u0001\u0000\u0000\u0000"+
		"gh\u0005\u000b\u0000\u0000hi\u0005+\u0000\u0000ij\u0005\u001a\u0000\u0000"+
		"jk\u0003\u0012\t\u0000kl\u0005\u001b\u0000\u0000lm\u0005 \u0000\u0000"+
		"mn\u0003\u0010\b\u0000n\u000f\u0001\u0000\u0000\u0000oy\u0005+\u0000\u0000"+
		"py\u0005(\u0000\u0000qy\u0005)\u0000\u0000ry\u0005*\u0000\u0000st\u0005"+
		"\u0001\u0000\u0000tu\u0005\u001c\u0000\u0000uv\u0005%\u0000\u0000vw\u0005"+
		"\u001d\u0000\u0000wy\u0003\u0010\b\u0000xo\u0001\u0000\u0000\u0000xp\u0001"+
		"\u0000\u0000\u0000xq\u0001\u0000\u0000\u0000xr\u0001\u0000\u0000\u0000"+
		"xs\u0001\u0000\u0000\u0000y\u0011\u0001\u0000\u0000\u0000z{\u0003\u0016"+
		"\u000b\u0000{|\u0003\u0014\n\u0000|\u0013\u0001\u0000\u0000\u0000}~\u0005"+
		"\"\u0000\u0000~\u0081\u0003\u0012\t\u0000\u007f\u0081\u0001\u0000\u0000"+
		"\u0000\u0080}\u0001\u0000\u0000\u0000\u0080\u007f\u0001\u0000\u0000\u0000"+
		"\u0081\u0015\u0001\u0000\u0000\u0000\u0082\u0083\u0005+\u0000\u0000\u0083"+
		"\u0084\u0005 \u0000\u0000\u0084\u0088\u0003\u0010\b\u0000\u0085\u0088"+
		"\u0005$\u0000\u0000\u0086\u0088\u0001\u0000\u0000\u0000\u0087\u0082\u0001"+
		"\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087\u0086\u0001"+
		"\u0000\u0000\u0000\u0088\u0017\u0001\u0000\u0000\u0000\u0089\u008a\u0003"+
		"\u001c\u000e\u0000\u008a\u008b\u0003\u001a\r\u0000\u008b\u0019\u0001\u0000"+
		"\u0000\u0000\u008c\u008d\u0005\u001e\u0000\u0000\u008d\u008e\u0005\t\u0000"+
		"\u0000\u008e\u008f\u0003\u0002\u0001\u0000\u008f\u0090\u0005\u001f\u0000"+
		"\u0000\u0090\u0093\u0001\u0000\u0000\u0000\u0091\u0093\u0001\u0000\u0000"+
		"\u0000\u0092\u008c\u0001\u0000\u0000\u0000\u0092\u0091\u0001\u0000\u0000"+
		"\u0000\u0093\u001b\u0001\u0000\u0000\u0000\u0094\u0095\u0003 \u0010\u0000"+
		"\u0095\u0096\u0003\u001e\u000f\u0000\u0096\u001d\u0001\u0000\u0000\u0000"+
		"\u0097\u0098\u0005\u0012\u0000\u0000\u0098\u009b\u0003\u001c\u000e\u0000"+
		"\u0099\u009b\u0001\u0000\u0000\u0000\u009a\u0097\u0001\u0000\u0000\u0000"+
		"\u009a\u0099\u0001\u0000\u0000\u0000\u009b\u001f\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0003$\u0012\u0000\u009d\u009e\u0003\"\u0011\u0000\u009e"+
		"!\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u0011\u0000\u0000\u00a0\u00a3"+
		"\u0003 \u0010\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u009f\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a1\u0001\u0000\u0000\u0000\u00a3#\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a5\u0003(\u0014\u0000\u00a5\u00a6\u0003&\u0013\u0000"+
		"\u00a6%\u0001\u0000\u0000\u0000\u00a7\u00a8\u0007\u0000\u0000\u0000\u00a8"+
		"\u00ab\u0003(\u0014\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a7"+
		"\u0001\u0000\u0000\u0000\u00aa\u00a9\u0001\u0000\u0000\u0000\u00ab\'\u0001"+
		"\u0000\u0000\u0000\u00ac\u00ad\u0003,\u0016\u0000\u00ad\u00ae\u0003*\u0015"+
		"\u0000\u00ae)\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0001\u0000\u0000"+
		"\u00b0\u00b3\u0003(\u0014\u0000\u00b1\u00b3\u0001\u0000\u0000\u0000\u00b2"+
		"\u00af\u0001\u0000\u0000\u0000\u00b2\u00b1\u0001\u0000\u0000\u0000\u00b3"+
		"+\u0001\u0000\u0000\u0000\u00b4\u00b5\u00030\u0018\u0000\u00b5\u00b6\u0003"+
		".\u0017\u0000\u00b6-\u0001\u0000\u0000\u0000\u00b7\u00b8\u0007\u0002\u0000"+
		"\u0000\u00b8\u00bb\u0003,\u0016\u0000\u00b9\u00bb\u0001\u0000\u0000\u0000"+
		"\u00ba\u00b7\u0001\u0000\u0000\u0000\u00ba\u00b9\u0001\u0000\u0000\u0000"+
		"\u00bb/\u0001\u0000\u0000\u0000\u00bc\u00bd\u0007\u0003\u0000\u0000\u00bd"+
		"\u00c0\u00030\u0018\u0000\u00be\u00c0\u00032\u0019\u0000\u00bf\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bf\u00be\u0001\u0000\u0000\u0000\u00c01\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u00036\u001b\u0000\u00c2\u00c3\u00034\u001a\u0000"+
		"\u00c33\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005\u001c\u0000\u0000\u00c5"+
		"\u00c6\u0003\u0018\f\u0000\u00c6\u00c7\u0005\u001d\u0000\u0000\u00c7\u00c8"+
		"\u00034\u001a\u0000\u00c8\u00cb\u0001\u0000\u0000\u0000\u00c9\u00cb\u0001"+
		"\u0000\u0000\u0000\u00ca\u00c4\u0001\u0000\u0000\u0000\u00ca\u00c9\u0001"+
		"\u0000\u0000\u0000\u00cb5\u0001\u0000\u0000\u0000\u00cc\u00d8\u0005&\u0000"+
		"\u0000\u00cd\u00d8\u0005%\u0000\u0000\u00ce\u00d8\u0005\'\u0000\u0000"+
		"\u00cf\u00d0\u0005+\u0000\u0000\u00d0\u00d8\u00038\u001c\u0000\u00d1\u00d2"+
		"\u0005\u001a\u0000\u0000\u00d2\u00d3\u0003>\u001f\u0000\u00d3\u00d4\u0005"+
		"\u001b\u0000\u0000\u00d4\u00d8\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005"+
		"\u001e\u0000\u0000\u00d6\u00d8\u0003<\u001e\u0000\u00d7\u00cc\u0001\u0000"+
		"\u0000\u0000\u00d7\u00cd\u0001\u0000\u0000\u0000\u00d7\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d7\u00cf\u0001\u0000\u0000\u0000\u00d7\u00d1\u0001\u0000"+
		"\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d87\u0001\u0000\u0000"+
		"\u0000\u00d9\u00da\u0005\u001a\u0000\u0000\u00da\u00e1\u0005\u001b\u0000"+
		"\u0000\u00db\u00dc\u0005\u001a\u0000\u0000\u00dc\u00dd\u0003>\u001f\u0000"+
		"\u00dd\u00de\u0005\u001b\u0000\u0000\u00de\u00e1\u0001\u0000\u0000\u0000"+
		"\u00df\u00e1\u0001\u0000\u0000\u0000\u00e0\u00d9\u0001\u0000\u0000\u0000"+
		"\u00e0\u00db\u0001\u0000\u0000\u0000\u00e0\u00df\u0001\u0000\u0000\u0000"+
		"\u00e19\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005\u0002\u0000\u0000\u00e3"+
		"\u00e4\u0003\u0018\f\u0000\u00e4\u00e5\u0005\u001f\u0000\u0000\u00e5\u00e8"+
		"\u0001\u0000\u0000\u0000\u00e6\u00e8\u0001\u0000\u0000\u0000\u00e7\u00e2"+
		"\u0001\u0000\u0000\u0000\u00e7\u00e6\u0001\u0000\u0000\u0000\u00e8;\u0001"+
		"\u0000\u0000\u0000\u00e9\u00ea\u0003\u0018\f\u0000\u00ea\u00eb\u0005#"+
		"\u0000\u0000\u00eb\u00ec\u0003\u0018\f\u0000\u00ec\u00ed\u0005\u001f\u0000"+
		"\u0000\u00ed\u0107\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005\u0005\u0000"+
		"\u0000\u00ef\u00f0\u0003\u0018\f\u0000\u00f0\u00f1\u0005\u0006\u0000\u0000"+
		"\u00f1\u00f2\u0003\u0018\f\u0000\u00f2\u00f3\u0003:\u001d\u0000\u00f3"+
		"\u0107\u0001\u0000\u0000\u0000\u00f4\u00f5\u0005\n\u0000\u0000\u00f5\u00f6"+
		"\u0003\u0018\f\u0000\u00f6\u00f7\u0005 \u0000\u0000\u00f7\u00f8\u0003"+
		"\u0018\f\u0000\u00f8\u00f9\u0005\u001f\u0000\u0000\u00f9\u0107\u0001\u0000"+
		"\u0000\u0000\u00fa\u00fb\u0005\u0003\u0000\u0000\u00fb\u00fc\u0005+\u0000"+
		"\u0000\u00fc\u00fd\u0005#\u0000\u0000\u00fd\u00fe\u0003\u0018\f\u0000"+
		"\u00fe\u00ff\u0005\"\u0000\u0000\u00ff\u0100\u0003\u0018\f\u0000\u0100"+
		"\u0101\u0005\"\u0000\u0000\u0101\u0102\u0003\u0018\f\u0000\u0102\u0103"+
		"\u0005 \u0000\u0000\u0103\u0104\u0003\u0018\f\u0000\u0104\u0105\u0005"+
		"\u001f\u0000\u0000\u0105\u0107\u0001\u0000\u0000\u0000\u0106\u00e9\u0001"+
		"\u0000\u0000\u0000\u0106\u00ee\u0001\u0000\u0000\u0000\u0106\u00f4\u0001"+
		"\u0000\u0000\u0000\u0106\u00fa\u0001\u0000\u0000\u0000\u0107=\u0001\u0000"+
		"\u0000\u0000\u0108\u0109\u0003\u0018\f\u0000\u0109\u010a\u0003@ \u0000"+
		"\u010a?\u0001\u0000\u0000\u0000\u010b\u010c\u0005\"\u0000\u0000\u010c"+
		"\u010f\u0003>\u001f\u0000\u010d\u010f\u0001\u0000\u0000\u0000\u010e\u010b"+
		"\u0001\u0000\u0000\u0000\u010e\u010d\u0001\u0000\u0000\u0000\u010fA\u0001"+
		"\u0000\u0000\u0000\u0012KQx\u0080\u0087\u0092\u009a\u00a2\u00aa\u00b2"+
		"\u00ba\u00bf\u00ca\u00d7\u00e0\u00e7\u0106\u010e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}