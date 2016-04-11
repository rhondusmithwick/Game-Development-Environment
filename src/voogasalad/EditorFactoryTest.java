//package voogasalad;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import view.Editor;
//import view.EditorFactory;
//
//public class EditorFactoryTest {
//
//	@Test
//	public void createEditorEntity() {
//		EditorFactory factory = new EditorFactory();
//		Editor editorEntity = factory.createEditor("EditorEntity");
//		assertEquals("view.EditorEntity", editorEntity.getClass().getName());
//	}
//
//	@Test(expected = NullPointerException.class)
//	public void testErrorIfInvalidEditor() {
//		EditorFactory factory = new EditorFactory();
//		Editor editorEntity = factory.createEditor("This is an invalid editor");
//		assertEquals(null, editorEntity.getClass().getName());
//
//	}
//}
