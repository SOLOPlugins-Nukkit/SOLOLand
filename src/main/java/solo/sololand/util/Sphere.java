package solo.sololand.util;

import java.util.ArrayList;

public class Sphere {
	
	private Sphere(){
		
	}
	
	public static ArrayList<int[]> getElements(int centerX, int centerY, int centerZ, int radius){
		int minX = centerX - radius;
		int maxX = centerX + radius;
		int minY = centerY - radius;
		int maxY = centerY + radius;
		int minZ = centerZ - radius;
		int maxZ = centerZ + radius;
		ArrayList<int[]> ret = new ArrayList<int[]>();

		for(int x = minX; x <= maxX; x++){
			for(int y = minY; y <= maxY; y++){
				for(int z = minZ; z <= maxZ; z++){
					double diff = Sphere.getDiff(x, y, z, centerX, centerY, centerZ);
					if(diff < radius - 0.2){
						int[] el = new int[3];
						el[0] = x; el[1] = y; el[2] = z;
						ret.add(el);
					}
				}
			}
		}
		return ret;
	}

	protected static double getDiff(int startX, int startY, int startZ, int endX, int endY, int endZ){
		double xzDiff = Math.sqrt(Math.pow(Math.abs(startX - endX), 2) + Math.pow(Math.abs(startZ - endZ), 2));
		return Math.sqrt(Math.pow(xzDiff, 2) + Math.pow(Math.abs(startY - endY), 2));
	}
}