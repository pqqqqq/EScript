package com.pqqqqq.escript.lang.phrase.block;

import com.pqqqqq.escript.lang.exception.MissingIfChainException;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.line.RunVessel;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;

import java.util.Optional;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <pre>
 * The else phrase, which runs its block if the other if/else if chain conditions are false
 * Examples:
 *      <code>else
 *      or else</code>
 * </pre>
 */
public class ElsePhrase implements Phrase {
    private static final ElsePhrase INSTANCE = new ElsePhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("or? else if? $Condition*", true)
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static ElsePhrase instance() {
        return INSTANCE;
    }

    private ElsePhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        Optional<RunVessel> previousIfOptional = ctx.getScript().getVesselAtTab(ctx.getLine().getTabulations());
        RunVessel previousIf = previousIfOptional.orElseThrow(() -> new MissingIfChainException("An if must be placed before an else statement"));

        Phrase phrase = previousIf.getContext().getLine().getPhrase();
        Result result = previousIf.getResult();
        boolean previousResult = ((Result.Success) result).getLiteralValue().asBoolean();

        boolean condition = ctx.getLiteral("Condition", true).asBoolean();
        if ((phrase instanceof IfPhrase || phrase instanceof ElsePhrase) && !previousResult && condition) {
            ctx.executeBlock();
        }

        return Result.success(condition || previousResult);
    }
}
