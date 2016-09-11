package com.pqqqqq.escript.lang.phrase.action;

import com.pqqqqq.escript.lang.data.Literal;
import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by Kevin on 2016-08-31.
 *
 * <pre>
 * The create variable phrase, which will create a variable
 * Examples:
 *      <code>create var with value "hello"
 *      create var with 1
 *      create var</code>
 * </pre>
 */
public class CreateVariable implements Phrase {
    private static final CreateVariable INSTANCE = new CreateVariable();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("create ^Name with? value* $Value*")

            /*Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)$", Pattern.CASE_INSENSITIVE),
            Pattern.compile("^(send(\\s+?))?(message|msg)(\\s+?)(?<Message>\\S+?)(\\s+?)" +
                    StringUtils.from("to").getOutsideQuotePattern() + "(\\s+?)(?<Player>.+?)$", Pattern.CASE_INSENSITIVE)*/
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static CreateVariable instance() {
        return INSTANCE;
    }

    private CreateVariable() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        String name = ctx.getStrarg("Name");
        Literal value = ctx.getLiteral("Value", (Object) null);

        ctx.getScript().create(name, value);
        return Result.success();
    }
}
