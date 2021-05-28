package gui;

import source.Dimensions;
import source.Images;

public class Main {	
	public static void main(String[] args) {
		// 모든 게임에 공통으로 해당하는 클래스 (Source class)
		
		// 크기 정보를 담은 클래스
		Dimensions dim = new Dimensions();
		// 게임 이미지 클래스
		Images images = new Images(dim);
		
		new WaitFrame(dim, images);
	}

}