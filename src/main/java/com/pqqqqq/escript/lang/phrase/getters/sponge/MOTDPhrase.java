package com.pqqqqq.escript.lang.phrase.getters.sponge;

import com.pqqqqq.escript.lang.line.Context;
import com.pqqqqq.escript.lang.phrase.Phrase;
import com.pqqqqq.escript.lang.phrase.Result;
import com.pqqqqq.escript.lang.phrase.syntax.Syntax;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 * Created by Kevin on 2016-09-02.
 * <p>
 * <pre>
 * The MOTD phrase, which gets the message of the day
 * </pre>
 */
public class MOTDPhrase implements Phrase {
    private static final MOTDPhrase INSTANCE = new MOTDPhrase();
    private static final Syntax[] SYNTAXES = {
            Syntax.compile("motd"),
            Syntax.compile("message of the? day")
    };

    /**
     * Gets the main instance
     *
     * @return the instance
     */
    public static MOTDPhrase instance() {
        return INSTANCE;
    }

    private MOTDPhrase() {
    }

    @Override
    public Syntax[] getSyntaxes() {
        return SYNTAXES;
    }

    @Override
    public Result execute(Context ctx) {
        return Result.success(TextSerializers.PLAIN.serialize(Sponge.getServer().getMotd()));
    }
}
