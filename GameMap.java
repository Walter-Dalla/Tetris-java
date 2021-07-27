import java.awt.Color;

class GameMap {
	private Color[][] map;
	private int width;
	private int height;

	public GameMap(int width, int height) {
		map = new Color[width][height];

		this.width = width;
		this.height = height;
		SetBackgroundColor();
		PrintBorders();
	}

	private void SetBackgroundColor(){
    
		for (int columnIndex = 0; columnIndex < map.length -0; columnIndex++) {
			Color[] gameMapLine = map[columnIndex];

			for (int lineIndex = 0; lineIndex < gameMapLine.length -0; lineIndex++) {
        
				gameMapLine[lineIndex] = getMapBackgroundColor();
			}
		}
	}

	private void PrintBorders(){
		for (int colIndex = 0; colIndex < map[0].length -1; colIndex++) {
			map[0][colIndex] = Color.GRAY;
			map[map.length -1][colIndex] = Color.GRAY;
		}

		for (int lineIndex = 0; lineIndex < map.length; lineIndex++) {
			map[lineIndex][map[0].length -1] = Color.GRAY;
		}
	}

	public void clearRow(int rowIndex){
		int mapOffset = 1;

		for(int colIndex = mapOffset; colIndex < getWidth(); colIndex++){
			setMapBlock(colIndex, rowIndex, getMapBackgroundColor());
		}
	}

	public Boolean checkIfRowIsFull(int rowIndex){
		Boolean isRowFull = false;

		for(int colIndex = 0; colIndex < getWidth(); colIndex++){
			isRowFull = getMapBlock(colIndex, rowIndex) != getMapBackgroundColor();
	
			if(isRowFull == false) {
				break;
			}
		}

		return isRowFull;
	}

	public int getMapOffSet(){
		return 1;
	}

	public void applyGravity(int rowIndex){
		for(int rowIndex2 = rowIndex; rowIndex2 > 0; rowIndex2--) {
			for(int colIndex = getMapOffSet(); colIndex < getWidth(); colIndex++){
				Color topColor = getMapBlock(colIndex, rowIndex2-1);
				Color bottomColor = getMapBlock(colIndex, rowIndex2);

				if(bottomColor == getMapBackgroundColor()) {
					setMapBlock(colIndex, rowIndex2, topColor);
					setMapBlock(colIndex, rowIndex2-1, getMapBackgroundColor());
				}
			}
		}
	}


	public Color[][] getMap(){
		return map;
	}

	public Color getMapBlock(int colIndex, int lineIndex){
		return map[colIndex][lineIndex];
	}

	public void setMapBlock(int colIndex, int lineIndex, Color color){
		map[colIndex][lineIndex] = color;
	}

	public void clearMapBlock(int colIndex, int lineIndex){
		map[colIndex][lineIndex] = getMapBackgroundColor();
	}

	public int getHeight(){
		return height;
	}

	public int getWidth(){
		return width - 1;
	}

	public Color getMapBackgroundColor(){
		return Color.BLACK;
	}
}