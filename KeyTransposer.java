public class KeyTransposer{

	// true for sharp, false for flat
	private static final boolean[] KEY_SIG_HAS_SHARPS = new boolean[]{
	true, false, true,  // C, Db, D
	false, true, false, // Eb, E, F
	true, true, false,   // F#, G, Ab
	true, false, true}; // A, Bb, B

	public static void main(String[] args){

		if (args.length < 3){
			System.err.println("Insufficent arguments");
			System.err.println();
		}

		String fromKeyString = args[0];
		String toKeyString = args[1];
		String[] noteStrings = new String[args.length - 2];
		System.arraycopy(args, 2, noteStrings, 0, noteStrings.length);

		int fromKeyNote = parseNote(fromKeyString);
		int toKeyNote = parseNote(toKeyString);
		int notes[] = new int[noteStrings.length];
		int transposedNotes[] = new int[notes.length];
		int transpositionInterval = toKeyNote - fromKeyNote;

		for (int x = 0; x < notes.length; ++x){
			notes[x] = parseNote(noteStrings[x]);
			transposedNotes[x] = normalizeNoteValue(
			notes[x] + transpositionInterval);
			System.out.print(noteToString(transposedNotes[x],
			KEY_SIG_HAS_SHARPS[toKeyNote]));
			if (x < transposedNotes.length - 1) System.out.print(" ");
		}

		System.out.println();
		System.out.println();

	}

	private static int parseNote(String noteString){
		if (noteString.length() < 1){
			throw new Error("Empty string");
		}
		int note;
		switch (noteString.charAt(0)){
			case 'c': case 'C':
			note = 0; break;
			case 'd': case 'D':
			note = 2; break;
			case 'e': case 'E':
			note = 4; break;
			case 'f': case 'F':
			note = 5; break;
			case 'g': case 'G':
			note = 7; break;
			case 'a': case 'A':
			note = 9; break;
			case 'b': case 'B':
			note = 11; break;
			default:
			throw new Error("Bad note letter");
		}
		if (noteString.length() == 1){
			return note;
		}
		switch (noteString.charAt(1)){
			case '#':
			note++; break;
			case 'b':
			note--; break;
			default:
			throw new Error("Bad note symbol");
		}
		note = normalizeNoteValue(note);
		if (noteString.length() == 2){
			return note;
		}
		throw new Error("Extra chars in note name");
	}

	private static int normalizeNoteValue(int note){
		return (note + 24) % 12;
	}

	private static String noteToString(int note, boolean sharps){
		String noteString = "";
		switch (note){
			case 0: noteString = "C"; break;
			case 1: noteString = sharps ? "C#" : "Db"; break;
			case 2: noteString = "D"; break;
			case 3: noteString = sharps ? "D#" : "Eb"; break;
			case 4: noteString = "E"; break;
			case 5: noteString = "F"; break;
			case 6: noteString = sharps ? "F#" : "Gb"; break;
			case 7: noteString = "G"; break;
			case 8: noteString = sharps ? "G#" : "Ab"; break;
			case 9: noteString = "A"; break;
			case 10: noteString = sharps ? "A#" : "Bb"; break;
			case 11: noteString = "B"; break;
			default: throw new Error("invalid note value");
		}
		return noteString;
	}
/*
	public static class Note{

		private int ordinal; // 0,1,2... 11 for C,C#/Db,D...B

		public Note(int ordinal){
			this.ordinal = ordinal;
		}

		public int getOrdinal(){
			return ordinal;
		}

		public String toString(){
			return KeyTransposer.noteToString(ordinal);
		}

	}
*/
}
