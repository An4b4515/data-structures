package algorithms;

import java.util.List;
import java.util.Comparator;

public final class Sort {
    public static <T> void bubble_sort(List<T> ls, Comparator<T> callback) {	// Functional interface ...
        for (int i = ls.size()-1; i > 0; --i) {
            boolean isSorted = true;

            for (int j = 0; j < i; ++j)
                if ( callback.compare(ls.get(j), ls.get(j+1)) > 0 ) {
                    isSorted = false;

                    T temp = ls.get(j);
                    ls.set( j, ls.get(j+1) );
                    ls.set( j+1, temp );
                }

            if (isSorted)
                break;
        }
    }
    public static <T extends Comparable<T>> void bubble_sort(List<T> ls) {
        for (int i = ls.size()-1; i > 0; --i) {
            boolean isSorted = true;

            for (int j = 0; j < i; ++j)
                if ( ls.get(j).compareTo(ls.get(j+1)) > 0 ) {
                    isSorted = false;

                    T temp = ls.get(j);
                    ls.set(j, ls.get(j+1));
                    ls.set(j+1, temp);
                }

            if (isSorted)
                break;
        }
    }


    public static <T extends Comparable<T>> void insertion_sort(List<T> ls) {
        for (int i = 1; i < ls.size(); ++i) {
            int j = i;

            while ( (j > 0) && (ls.get(j-1).compareTo(ls.get(j)) > 0) ) {
                T temp = ls.get(j);

                ls.set(j, ls.get(j-1));
                ls.set(j-1, temp);
                --j;
            }
        }
    }
    public static <T> void insertion_sort(List<T> ls, Comparator<T> comp) {
        for (int i = 1; i < ls.size(); ++i) {
            int j = i;

            while ( (j > 0) && (comp.compare(ls.get(j-1), ls.get(j)) > 0) ) {
                T temp = ls.get(j);

                ls.set(j, ls.get(j-1));
                ls.set(j-1, temp);
                --j;
            }
        }
    }


    public static <T extends Comparable<T>> void selection_sort(List<T> ls) {
        for (int i = 0; i < ls.size()-1; ++i) {
            int min = i;

            for (int j = (i+1); j < ls.size(); ++j)
                if ( ls.get(j).compareTo( ls.get(min) ) < 0 )
                    min = j;

            if (i != min) {
                T temp = ls.get(i);
                ls.set( i, ls.get(min) );
                ls.set( min, temp );
            }
        }
    }
    public static <T> void selection_sort(List<T> ls, Comparator<T> comp) {
        for (int i = 0; i < ls.size()-1; ++i) {
            int min = i;

            for (int j = (i+1); j < ls.size(); ++j)
                if ( comp.compare(ls.get(j), ls.get(min)) < 0 )
                    min = j;

            if (i != min) {
                T temp = ls.get(i);
                ls.set( i, ls.get(min) );
                ls.set( min, temp );
            }
        }
    }
}

