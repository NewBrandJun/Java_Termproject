package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Source.Size;
import User.Player;
import game.GameInfo;

public class MouseAction extends MouseAdapter{
	GameFrame jframe;
	GameBoard board;
	Size map_size;
	GameInfo game_rule;
	
	GameSub score;
	
	float[] rangeX_below;
	float[] rangeX_upper;
	float[] rangeY_below;
	float[] rangeY_upper;
	
	public MouseAction(GameFrame _jframe, GameBoard _board, Size _map_size, GameInfo _game_rule, GameSub _score){
		jframe = _jframe;
		board = _board;
		map_size = _map_size;
		game_rule = _game_rule;
		score = _score;
		
		// x�� click������ range
		rangeX_below = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_below[i] = 152+(i*map_size.getCell());
		}
		rangeX_upper = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeX_upper[i] = 182+(i*map_size.getCell());
		}

		// y�� click������ range
		rangeY_below = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeY_below[i] = 124+(i*map_size.getCell());
		}
		rangeY_upper = new float[8];
		for(int i= 0; i < 8;i++) {
			rangeY_upper[i] = 154+(i*map_size.getCell());
		}
		
	}
	
	
	
	@Override
	public void mousePressed(MouseEvent me) {
		// �� ĭ�� 41.5
		
		// x : 147 ~ 187 -> 0
		// y : 119 ~ 159 -> 0
		// ���� : ����,���� 10���� ����
		// x : 152 ~ 182 -> 0
		// y : 124 ~ 154 -> 0
		
		int x = (int)me.getX();
		int y = (int)me.getY();
		
		if(x < 152 || y < 124 || x > 472 || y > 444) {
			return;
		}
		
		// x range check
		for(int i =0; i < 8;i++) {
			if(x >= rangeX_below[i]) {
				if(x <= rangeX_upper[i]) {
					// correct range
					x = i;
					break;
				}
			}else {
				// incorrect range
				return;
			}
		}		
		
		// y range check
		for(int i =0; i < 8;i++) {
			if(y >= rangeY_below[i]) {
				if(y <= rangeY_upper[i]) {
					// correct range
					y = i;
					break;
				}
			}else {
				// incorrect range
				return;
			}
		}

				
		// map�� ����ִ� ��쿡�� 
		if(game_rule.getMap()[y][x] == 0) {
			Player player = new Player(y,x,game_rule.getCurPlayer());
			game_rule.inputWord(player);
			game_rule.nextPlayer(game_rule.getCurPlayer());
			score.changeTurn();
			score.changeCount();
			
			board.repaint();
			if(game_rule.endGame(player)==true) {
				String ms;
				if(player.getColor()==1) {
					ms="�˵��¸�!";
				}
				else if(player.getColor()==2) {
					ms="�鵹�¸�!";
				}
//				showWin(ms);
				game_rule.init();
			}
		}else return;
		
				
	}
}
