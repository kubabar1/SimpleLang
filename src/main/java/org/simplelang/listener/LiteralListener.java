package org.simplelang.listener;

import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;

import java.util.Objects;

public class LiteralListener extends org.simplelang.SimpleLangBaseListener {

    /*@Override
    public void exitLiteral(org.simplelang.SimpleLangParser.LiteralContext ctx) {
        if (Objects.nonNull(ctx.numberLiteral())) {
            if (Objects.nonNull(ctx.numberLiteral().FloatingPointLiteral())) {
                VariablesContainer.getInstance().pushToStack(new Value(ctx.numberLiteral().FloatingPointLiteral().getText(), VariableType.FLOAT));
            } else if (Objects.nonNull(ctx.numberLiteral().IntegerLiteral())) {
                VariablesContainer.getInstance().pushToStack(new Value(ctx.numberLiteral().IntegerLiteral().getText(), VariableType.INT));
            }
        } else if (Objects.nonNull(ctx.BooleanLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.BooleanLiteral().getText(), VariableType.BOOLEAN));
        } else if (Objects.nonNull(ctx.StringLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.StringLiteral().getText(), VariableType.STRING));
        } else if (Objects.nonNull(ctx.NullLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.NullLiteral().getText(), VariableType.NULL));
        }
    }*/

}
