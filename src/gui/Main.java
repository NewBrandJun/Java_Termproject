package gui;

import source.Dimensions;
import source.Images;

public class Main {	
	public static void main(String[] args) {
		// ��� ���ӿ� �������� �ش��ϴ� Ŭ���� (Source class)
		
		// ũ�� ������ ���� Ŭ����
		Dimensions dim = new Dimensions();
		// ���� �̹��� Ŭ����
		Images images = new Images(dim);
		
		new WaitFrame(dim, images);
	}

}