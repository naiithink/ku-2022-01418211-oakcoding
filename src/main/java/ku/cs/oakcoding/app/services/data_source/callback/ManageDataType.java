/**
 * @file ManageDataType.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.callback;

public interface ManageDataType<T> {

        /**
         * ?: instanceCreate(String) -> newInstance(String)
         */
        public T instanceCreate(String[] data);

        public String instanceWrite(Object obj);

        public String getKey(String[] data);

        public String getQuoteFormat(Object o);

        public String formatCSV(Object o);
}
