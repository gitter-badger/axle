package axle.visualize

class JungUndirectedGraphVisualization {

  import javax.swing.JFrame
  import axle.graph.JungUndirectedGraphFactory._

  def draw[VP, EP](jug: JungUndirectedGraph[VP, EP]): Unit = {

    type V = jug.type#V
    type E = jug.type#E

    import java.awt.{ Dimension, BasicStroke, Color, Paint, Stroke }
    import java.awt.event.MouseEvent
    import edu.uci.ics.jung.algorithms.layout.{ FRLayout, Layout }
    import edu.uci.ics.jung.graph.{ Graph, SparseGraph }
    import edu.uci.ics.jung.visualization.VisualizationViewer
    import edu.uci.ics.jung.visualization.control.{ PluggableGraphMouse, PickingGraphMousePlugin, TranslatingGraphMousePlugin }
    import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position
    import org.apache.commons.collections15.Transformer

    val width = 700
    val height = 700
    val border = 50

    val layout = new FRLayout(jug.getStorage)
    layout.setSize(new Dimension(width, height))
    val vv = new VisualizationViewer[V, E](layout) // interactive
    vv.setPreferredSize(new Dimension(width + border, height + border))

    val vertexPaint = new Transformer[V, Paint]() {
      def transform(i: V): Paint = Color.GREEN
    }

    val dash = List(10.0f).toArray

    val edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)

    val edgeStrokeTransformer = new Transformer[E, Stroke]() {
      def transform(edge: E) = edgeStroke
    }

    val vertexLabelTransformer = new Transformer[V, String]() {
      def transform(vertex: V) = vertex.toString()
    }

    val edgeLabelTransformer = new Transformer[E, String]() {
      def transform(edge: E) = edge.toString()
    }

    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint)
    vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer)
    vv.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer)
    vv.getRenderContext().setEdgeLabelTransformer(edgeLabelTransformer)
    vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR)

    val gm = new PluggableGraphMouse()
    gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1))
    gm.add(new PickingGraphMousePlugin())
    vv.setGraphMouse(gm)

    val frame = new JFrame("graph name")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.getContentPane().add(vv)
    frame.pack()

    frame.setVisible(true)
  }

}
