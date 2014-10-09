package org.grooscript.gradle.asts

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.grooscript.gradle.template.Templates

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class TemplateEnhancerImpl implements ASTTransformation {

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (!nodes[0] instanceof AnnotationNode || !nodes[1] instanceof ClassNode) {
            return
        }

        ClassNode classNode = (ClassNode) nodes[1]

        def visitor = new MethodCallsVisitor()
        classNode.visitContents(visitor)
    }
}

class MethodCallsVisitor extends ClassCodeVisitorSupport {

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        if (statement.expression instanceof MethodCallExpression &&
                statement.expression.methodAsString == 'include') {
            def template
            try {
                def args = statement.expression.arguments

                args.expressions[0].mapEntryExpressions.each { MapEntryExpression mapEntryExpression ->
                    if (mapEntryExpression.keyExpression.text) {
                        template = mapEntryExpression.valueExpression.text
                    }
                }

                if (template) {
                    statement.expression = new MethodCallExpression(
                            new VariableExpression('this', ClassHelper.OBJECT_TYPE),
                            'yieldUnescaped',
                            new ArgumentListExpression([
                                    new MethodCallExpression(
                                            new ClassExpression(new ClassNode(Templates)),
                                            'applyTemplate',
                                            new ArgumentListExpression([
                                                    new ConstantExpression(template),
                                                    new VariableExpression('model', ClassHelper.MAP_TYPE)
                                            ])
                                    )
                            ])
                    )
                }
            } catch (e) {
                e.printStackTrace()
            }
        }
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return null
    }
}