package com.snakybo.snakemp.common.screen.components;

import static com.snakybo.sengine2d.core.Input.KeyCode.A;
import static com.snakybo.sengine2d.core.Input.KeyCode.B;
import static com.snakybo.sengine2d.core.Input.KeyCode.BACK;
import static com.snakybo.sengine2d.core.Input.KeyCode.C;
import static com.snakybo.sengine2d.core.Input.KeyCode.D;
import static com.snakybo.sengine2d.core.Input.KeyCode.DELETE;
import static com.snakybo.sengine2d.core.Input.KeyCode.E;
import static com.snakybo.sengine2d.core.Input.KeyCode.END;
import static com.snakybo.sengine2d.core.Input.KeyCode.F;
import static com.snakybo.sengine2d.core.Input.KeyCode.G;
import static com.snakybo.sengine2d.core.Input.KeyCode.H;
import static com.snakybo.sengine2d.core.Input.KeyCode.HOME;
import static com.snakybo.sengine2d.core.Input.KeyCode.I;
import static com.snakybo.sengine2d.core.Input.KeyCode.J;
import static com.snakybo.sengine2d.core.Input.KeyCode.K;
import static com.snakybo.sengine2d.core.Input.KeyCode.L;
import static com.snakybo.sengine2d.core.Input.KeyCode.LALT;
import static com.snakybo.sengine2d.core.Input.KeyCode.LCONTROL;
import static com.snakybo.sengine2d.core.Input.KeyCode.LEFT;
import static com.snakybo.sengine2d.core.Input.KeyCode.LSHIFT;
import static com.snakybo.sengine2d.core.Input.KeyCode.M;
import static com.snakybo.sengine2d.core.Input.KeyCode.MINUS;
import static com.snakybo.sengine2d.core.Input.KeyCode.N;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD0;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD1;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD2;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD3;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD4;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD5;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD6;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD7;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD8;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUMPAD9;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_0;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_1;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_2;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_3;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_4;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_5;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_6;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_7;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_8;
import static com.snakybo.sengine2d.core.Input.KeyCode.NUM_9;
import static com.snakybo.sengine2d.core.Input.KeyCode.O;
import static com.snakybo.sengine2d.core.Input.KeyCode.P;
import static com.snakybo.sengine2d.core.Input.KeyCode.PERIOD;
import static com.snakybo.sengine2d.core.Input.KeyCode.Q;
import static com.snakybo.sengine2d.core.Input.KeyCode.R;
import static com.snakybo.sengine2d.core.Input.KeyCode.RALT;
import static com.snakybo.sengine2d.core.Input.KeyCode.RCONTROL;
import static com.snakybo.sengine2d.core.Input.KeyCode.RIGHT;
import static com.snakybo.sengine2d.core.Input.KeyCode.RSHIFT;
import static com.snakybo.sengine2d.core.Input.KeyCode.S;
import static com.snakybo.sengine2d.core.Input.KeyCode.SPACE;
import static com.snakybo.sengine2d.core.Input.KeyCode.T;
import static com.snakybo.sengine2d.core.Input.KeyCode.U;
import static com.snakybo.sengine2d.core.Input.KeyCode.V;
import static com.snakybo.sengine2d.core.Input.KeyCode.W;
import static com.snakybo.sengine2d.core.Input.KeyCode.X;
import static com.snakybo.sengine2d.core.Input.KeyCode.Y;
import static com.snakybo.sengine2d.core.Input.KeyCode.Z;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.Input.KeyCode;
import com.snakybo.sengine2d.core.Input.MouseButton;
import com.snakybo.sengine2d.gui.GUIComponent;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.utils.Bounds;
import com.snakybo.sengine2d.utils.math.Vector3f;

public class GUIInputField extends GUIComponent {
	public static final int BIT_KEYS = 0x00;
	public static final int BIT_NUMS = 0x01;
	public static final int BIT_SPECIALS = 0x02;
	public static final int BIT_CONTROL = 0x03;
	
	private static final int[] AVAILABLE_KEYS = new int[] {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
	private static final int[] AVAILABLE_NUMS = new int[] {NUM_0, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9, NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9};
	private static final int[] AVAILABLE_SPECIALS = new int[] {MINUS, SPACE, PERIOD};
	private static final int[] AVAILABLE_CONTROL = new int[] {LEFT, RIGHT, BACK, DELETE, HOME, END};
	
	private List<Integer> availableKeys;
	
	private InputFieldHandler handler;
	
	private TrueTypeFont font;
	
	private Vector3f background;
	private Vector3f border;
	
	private Timer blinkerTimer;
	
	private Input input;
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private String oldValue;
	private String value;
	
	private int maxCharacters;
	
	private int oldCursorPos;
	private int cursorPos;
	
	private boolean isActive;
	
	private boolean showBlinker;
	
	public GUIInputField(String fontName, float fontSize, int x, int y, int width, int height, InputFieldHandler handler) {
		try {
			InputStream is = ResourceLoader.getResourceAsStream("./res/fonts/" + fontName);
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);			
			font = new TrueTypeFont(awtFont, true);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		
		availableKeys = new ArrayList<Integer>();
		
		value = "";
		
		border = new Vector3f(0, 0, 0);
		background = new Vector3f(1, 1, 1);
		
		maxCharacters = 64;
		
		setAllowBit(BIT_KEYS, true);
		setAllowBit(BIT_NUMS, true);
		setAllowBit(BIT_SPECIALS, true);
		setAllowBit(BIT_CONTROL, true);
		
		blinkerTimer = new Timer(650, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBlinker = !showBlinker;
			}
		});
	}
	
	@Override
	public void update(Input input, float delta) {
		if(this.input == null)
			this.input = input;
		
		if(input.isMouseDown(MouseButton.LEFT)) {
			if(input.getMouseX() >= x && input.getMouseX() <= x + width && input.getMouseY() >= y && input.getMouseY() <= y + height) {
				setIsActive(true);
			} else {
				if(isActive) {
					setIsActive(false);
					
					if(handler != null)
						handler.onFocusLost();
				}
			}
		}
		
		if(isActive) {
			int key = input.getLastKey();
		
			if(key != -1) {
				for(int i = 0; i < availableKeys.size(); i++) {				
					if(key == availableKeys.get(i)) {
						char c;
						
						if(key == MINUS) {
							if(input.getKey(KeyCode.LSHIFT) || input.getKey(KeyCode.RSHIFT)) {
								c = '_';
							} else {
								c = '-';
							}
						} else if(key == LEFT || key == RIGHT || key == BACK || key == DELETE) {
							c = 0;
						} else if(key == NUMPAD0) {
							c = '0';
						} else if(key == NUMPAD1) {
							c = '1';
						} else if(key == NUMPAD2) {
							c = '2';
						} else if(key == NUMPAD3) {
							c = '3';
						} else if(key == NUMPAD4) {
							c = '4';
						} else if(key == NUMPAD5) {
							c = '5';
						} else if(key == NUMPAD6) {
							c = '6';
						} else if(key == NUMPAD7) {
							c = '7';
						} else if(key == NUMPAD8) {
							c = '8';
						} else if(key == NUMPAD9) {
							c = '9';
						} else if(key == SPACE) {
							c = ' ';
						} else if(key == PERIOD) {
							c = '.';
						} else {
							c = Keyboard.getKeyName(key).charAt(0);
						}
						
						keyPressed(key, c);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawQuad(new Bounds(x - 2, x + width + 2, y - 2, y + height + 2), border);
		renderer.drawQuad(new Bounds(x, x + width, y, y + height), background);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		String drawValue = value;
		
		if(isActive && showBlinker)
			drawValue = value.substring(0, cursorPos) + "_" + (cursorPos >= value.length() ? "" : value.substring(cursorPos + 1));
		
		font.drawString(x + 5, y + 7, drawValue, Color.black);
		
		glBlendFunc(GL_ONE, GL_DST_ALPHA);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public void keyPressed(int key, char c) {
		if(key != -1) {
			if(key == V && (input.getKey(LCONTROL) || input.getKey(RCONTROL))) {
				String text = Sys.getClipboard();
				
				if(text != null) {
					paste(text);
				}
			}
			
			if(key == Z && (input.getKey(LCONTROL) || input.getKey(RCONTROL))) {
				if(oldValue != null) {
					undo(oldCursorPos, oldValue);
				}
			}
			
			if(input.getKey(LCONTROL) || input.getKey(RCONTROL) || input.getKey(LALT) || input.getKey(RALT))
				return;
		}
		
		if(key == LEFT) {
			if(cursorPos > 0)
				cursorPos--;
		} else if(key == RIGHT) {
			if(cursorPos < value.length())
				cursorPos++;
		} else if(key == BACK) {
			if(cursorPos > 0 && value.length() > 0) {
				if(cursorPos < value.length()) {
					value = value.substring(0, cursorPos - 1) + value.substring(cursorPos);
				} else {
					value = value.substring(0, cursorPos - 1);
				}
				
				cursorPos--;
			}
		} else if(key == DELETE) {
			if(value.length() > cursorPos) {
				value = value.substring(0, cursorPos) + value.substring(cursorPos + 1);
			}
		} else if(key == HOME) {
			cursorPos = 0;
		} else if(key == END) {
			cursorPos = value.length();
		} else if(c < 127 && c > 31 && value.length() < maxCharacters) {
			if(input.getKey(LSHIFT) || input.getKey(RSHIFT)) {
				c = Character.toUpperCase(c);
			} else {
				c = Character.toLowerCase(c);
			}
			
			if(cursorPos < value.length()) {
				value = value.substring(0, cursorPos) + c + value.substring(cursorPos);
			} else {
				value = value.substring(0, cursorPos) + c;
			}
			
			cursorPos++;
		}
	}
	
	private void paste(String text) {
		oldValue = text;
		oldCursorPos = cursorPos;
		
		for(int i = 0; i < value.length(); i++)
			keyPressed(-1, text.charAt(i));
	}
	
	private void undo(int oldCursorPos, String oldValue) {
		if(oldValue != null) {
			setText(value);
			setCursorPos(oldCursorPos);
		}
	}
	
	public void setBackground(Vector3f background) {
		this.background = background;
	}
	
	public void setBorder(Vector3f border) {
		this.border = border;
	}
	
	public void setText(String value) {
		this.value = value;
		
		if(cursorPos > value.length())
			cursorPos = value.length();
	}
	
	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
		
		if(isActive) {
			blinkerTimer.start();
		} else {
			blinkerTimer.stop();
		}
	}
	
	public void setAllowBit(int bit, boolean allowed) {
		switch(bit) {
		case BIT_KEYS:
			if(allowed) {
				for(int i = 0; i < AVAILABLE_KEYS.length; i++)
					if(!availableKeys.contains(AVAILABLE_KEYS[i]))
						availableKeys.add(AVAILABLE_KEYS[i]);
			} else {
				for(int i = 0; i < AVAILABLE_KEYS.length; i++)
					if(availableKeys.contains(AVAILABLE_KEYS[i]))
						availableKeys.remove((Object)AVAILABLE_KEYS[i]);
			}
			break;
		case BIT_NUMS:
			if(allowed) {
				for(int i = 0; i < AVAILABLE_NUMS.length; i++)
					if(!availableKeys.contains(AVAILABLE_NUMS[i]))
						availableKeys.add(AVAILABLE_NUMS[i]);
			} else {
				for(int i = 0; i < AVAILABLE_NUMS.length; i++)
					if(availableKeys.contains(AVAILABLE_NUMS[i]))
						availableKeys.remove((Object)AVAILABLE_NUMS[i]);
			}
			break;
		case BIT_SPECIALS:
			if(allowed) {
				for(int i = 0; i < AVAILABLE_SPECIALS.length; i++)
					if(!availableKeys.contains(AVAILABLE_SPECIALS[i]))
						availableKeys.add(AVAILABLE_SPECIALS[i]);
			} else {
				for(int i = 0; i < AVAILABLE_SPECIALS.length; i++)
					if(availableKeys.contains(AVAILABLE_SPECIALS[i]))
						availableKeys.remove((Object)AVAILABLE_SPECIALS[i]);
			}
			break;
		case BIT_CONTROL:
			if(allowed) {
				for(int i = 0; i < AVAILABLE_CONTROL.length; i++)
					if(!availableKeys.contains(AVAILABLE_CONTROL[i]))
						availableKeys.add(AVAILABLE_CONTROL[i]);
			} else {
				for(int i = 0; i < AVAILABLE_CONTROL.length; i++)
					if(availableKeys.contains(AVAILABLE_CONTROL[i]))
						availableKeys.remove((Object)AVAILABLE_CONTROL[i]);
			}
			break;
		}
	}
	
	public void setAllowCustom(int key, boolean allowed) {
		if(allowed) {
			if(!availableKeys.contains(key))
				availableKeys.add(key);
		} else {
			if(availableKeys.contains(key))
				availableKeys.remove((Object)key);
		}
	}
	
	public void setCursorPos(int pos) {
		cursorPos = pos;
		
		if(cursorPos > value.length())
			cursorPos = value.length();
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public interface InputFieldHandler {
		void onFocusLost();
	}
}
