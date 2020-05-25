package org.simplelang.listener.predicates;

import org.simplelang.SimpleLangParser.ComparisonStatementContext;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IfStatementPredicates {

    public static BiPredicate<ComparisonStatementContext, ComparisonStatementContext> numbersComparison = (leftStatement, rightStatement) ->
            Objects.nonNull(leftStatement.numberLiteral()) && Objects.nonNull(rightStatement.numberLiteral());

    public static BiPredicate<ComparisonStatementContext, ComparisonStatementContext> variablesComparison = (leftStatement, rightStatement) ->
            Objects.nonNull(leftStatement.variable()) && Objects.nonNull(rightStatement.variable());

    public static BiPredicate<ComparisonStatementContext, ComparisonStatementContext> variableAndNumberComparison = (leftStatement, rightStatement) ->
            Objects.nonNull(leftStatement.variable()) && Objects.nonNull(rightStatement.numberLiteral()) ||
                    Objects.nonNull(leftStatement.numberLiteral()) && Objects.nonNull(rightStatement.variable());

    public static BiPredicate<ComparisonStatementContext, ComparisonStatementContext> variableAndBooleanComparison = (leftStatement, rightStatement) ->
            Objects.nonNull(leftStatement.variable()) && Objects.nonNull(rightStatement.BooleanLiteral()) ||
                    Objects.nonNull(leftStatement.BooleanLiteral()) && Objects.nonNull(rightStatement.variable());

    public static Predicate<ComparisonStatementContext> isNumber = (statement) ->
            Objects.nonNull(statement.numberLiteral());

    public static Predicate<ComparisonStatementContext> isIntegerNumber = (statement) ->
            Objects.nonNull(statement.numberLiteral()) && Objects.nonNull(statement.numberLiteral().IntegerLiteral());

    public static Predicate<ComparisonStatementContext> isFloatingPointNumber = (statement) ->
            Objects.nonNull(statement.numberLiteral()) && Objects.nonNull(statement.numberLiteral().FloatingPointLiteral());

    public static Predicate<ComparisonStatementContext> isVariable = (statement) ->
            Objects.nonNull(statement.variable());

}
