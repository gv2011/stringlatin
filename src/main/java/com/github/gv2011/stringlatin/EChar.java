package com.github.gv2011.stringlatin;

import java.util.stream.IntStream;

public interface EChar extends CharSequence, Comparable<EChar>{

	short index();

	IntStream codepoints();

	@Override
	default int compareTo(final EChar other) {
		return index()-other.index();
	}

	boolean isGlyph();

}
