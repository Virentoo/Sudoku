package pl.Barszczewski.Kacper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MyJTextPanel extends JTextPane {
	
	Color eBColor = new Color(180, 180, 180);
	Color dBColor = new Color(160, 160, 160);
	Color wBColor = new Color(255, 40, 40);
	
	MyJTextPanel() {
		
	}
	
	public MyJTextPanel(int width, int height) {
		this.setBackground(new Color(235,235,235));
		this.setPreferredSize(new Dimension(13,13));
		this.setFont(new Font(null, Font.BOLD, 17));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setBackground(eBColor);
		this.setForeground(new Color(240, 240, 240));
		
		
		Set<KeyStroke> set = new HashSet<KeyStroke>();
		set.add(KeyStroke.getKeyStroke("TAB"));
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
		this.setDisabledTextColor(new Color(50, 50, 50));
		
		this.setStyledDocument(new DefaultStyledDocument() {
			
			int maxDocumentLength = 1;
			
			@Override
		    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		        if ((getLength() + str.length()) <= maxDocumentLength && !Character.isWhitespace(str.charAt(0)) && (int)(str.charAt(0)) > 48
		        		&& (int)(str.charAt(0)) < 58) {
		            super.insertString(offs, str, a);
		        } else {
		        	super.replace(offs - 1, str.length(), str, a);
		        }
		    }
		});
		
		StyledDocument doc = this.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		doc.setCharacterAttributes(0, doc.getLength(), center, false);
	}

	public void setValue(int i) {
		this.setText(String.valueOf(i));
	}
	
	@Override
	public void setText(String text) {
		if(Integer.parseInt(text) == 0) text = "";
		super.setText(text);
	}
	
	@Override
	public void setEnabled(boolean b) {
		if(b) this.setBackground(eBColor);
		else this.setBackground(dBColor);
		super.setEnabled(b);
	}
	
	public void wrong() {
		this.setBackground(wBColor);
	}
	
	public void correct() {
		this.setBackground(eBColor);
	}
	
	
}
