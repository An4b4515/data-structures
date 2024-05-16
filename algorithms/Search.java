package algorithms;

import java.util.List;
import java.util.Objects;

@FunctionalInterface
interface Formula<T extends Comparable<T>> {
    int position(List<T> ls, T item, int lo, int hi);
}

public final class Search {
    /* O(logn) average complexity, list needs to be sorted */
    public static <T extends Comparable<T>> int binary(List<T> ls, T item) {
        return perform_search(ls, item, Search::binary_formula);
    }
    
    /*
        Binary search improvement:
        - List elements need to be sorted.
        - O(log( log(n) )) average complexity if elements are evenly distributed
    */
    public static <T extends Comparable<T>> int interpolation(List<T> ls, T item) {
        return perform_search(ls, item, Search::interpolation_formula);
    }

    private static <T extends Comparable<T>> int perform_search(List<T> ls, T item, Formula<T> formula) {
        Objects.requireNonNull( item );
        
        int lo = 0,
            hi = ls.size() - 1;
        
        if (
            item.compareTo( ls.get(lo) ) >= 0
                &&
            item.compareTo( ls.get(hi) ) <= 0
        ) {
            while (lo <= hi) {
                int pos = formula.position(ls, item, lo, hi);
                
                if ( item.equals( ls.get(pos) ) )
                    return pos;	// Item found ...
                
                else if ( item.compareTo( ls.get(pos) ) < 0 )
                    hi = pos - 1;
                
                else
                    lo = pos + 1;
            }
        }
        
        return -1;	// Item not found ...
    }

    private static <T extends Comparable<T>> int binary_formula(List<T> ls, T item, int lo, int hi) {
        return (hi + lo) >> 1;	// Efficient integer division by 2 ...
    }

    private static <T extends Comparable<T>> int interpolation_formula(List<T> ls, T item, int lo, int hi) {
        return lo + (
            ( (hi - lo) * item.compareTo( ls.get(lo) ) )
            / ls.get(hi).compareTo( ls.get(lo) )
        );
    }
}

