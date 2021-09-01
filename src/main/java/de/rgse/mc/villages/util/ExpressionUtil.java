package de.rgse.mc.villages.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpressionUtil {

    private static ExpressionUtil instance;

    private final JexlEngine jexl = new JexlEngine();

    public static ExpressionUtil instance() {
        if (null == instance) {
            instance = new ExpressionUtil();
        }

        return instance;
    }

    public boolean evaluateEntity(Entity entity, String expressionString) {
        Expression expression = jexl.createExpression(expressionString);

        JexlContext context = new MapContext();
        context.set("entity", entity);

        Object evaluated = expression.evaluate(context);
        return evaluated != null && Boolean.parseBoolean(evaluated.toString());
    }
}
