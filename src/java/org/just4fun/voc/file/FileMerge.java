package org.just4fun.voc.file;

import java.util.concurrent.RecursiveAction;

public class FileMerge extends RecursiveAction {
	
	private VirtualFile virtualFile;
	
	public FileMerge(VirtualFile vf) {
		virtualFile = vf;
	}

	// TODO, pass a file array into this and then merge into one.
	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		merge();
	}
	
	private void merge() {

	}

}
