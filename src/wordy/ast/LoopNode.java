package wordy.ast;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

import wordy.interpreter.EvaluationContext;
import wordy.interpreter.LoopExited;

/**
 * Wordy’s only looping construct, essentially an infinite while loop. Repeatedly runs the `body`
 * statement until it encounters a LoopExitNode.
 * 
 * The interpreter implements this by catching a `LoopExited` exception to exit the loop.
 */
public class LoopNode extends StatementNode {
    private final StatementNode body;

    public LoopNode(StatementNode body) {
        this.body = body;
    }

    @Override
    public Map<String, ASTNode> getChildren() {
        return Map.of("body", body);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        LoopNode loopNode = (LoopNode) o;
        return body.equals(loopNode.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public String toString() {
        return "LoopNode{body=" + body + '}';
    }

    @Override
    protected void doRun(EvaluationContext context) {
        try {
            // Keep running the loop body until the LoopExited exception is thrown
            while (true) {
                body.run(context);
            }
        } catch (LoopExited e) {
            // Loop has exited, handle any final tasks if needed
        }
    }

    @Override
    public void compile(PrintWriter out) {
        out.println("while(true)"); // Start of the infinite loop
        body.compile(out); // Compile the body of the loop
    }

}
