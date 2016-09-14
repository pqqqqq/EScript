package com.pqqqqq.escript.lang.phrase.arithmetic;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The modulus phrase, which takes the modulus of two literals
 *
 * Some examples:
 *      <code>1 % 1
 *      2 mod 2</code>
 * </pre>
 */
public class ModulusPhrase implements Phrase {
    private static final ModulusPhrase INSTANCE = new ModulusPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("$Literal1 %|mod|modulus $Literal2"),
            Syntax.compile("modulus of? $Literal1 and $Literal2")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static ModulusPhrase instance() {
        return INSTANCE;
    }

    private ModulusPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Literal literal1 = ctx.getLiteral("Literal1");
        Literal literal2 = ctx.getLiteral("Literal2");

        return Result.success(literal1.mod(literal2));
    }
}
