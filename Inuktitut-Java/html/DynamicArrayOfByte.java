/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Créé le / Created on Feb 4, 2006
 * par / by Benoit Farley
 * 
 */
package html;

public class DynamicArrayOfByte {
    private byte[] data;  // An array to hold the data.
    private int addPosition = 0;
    public DynamicArrayOfByte() {
        // Constructor.
        data = new byte[1];  // Array will grow as necessary.
        addPosition = 0;
    }
    public DynamicArrayOfByte(int initialSize) {
        // Constructor.
        data = new byte[initialSize];  // Array will grow as necessary.
        addPosition = 0;
    }
    public DynamicArrayOfByte(byte[] bs) {
        data = bs;
        addPosition = bs.length;
    }
    public byte get(int position) {
        // Get the value from the specified position in the array.
        // Since all array positions are initially zero, when the
        // specified position lies outside the actual physical size
        // of the data array, a value of 0 is returned.
        // If position is negative, we return the value at 'position'
        // from the last element.
        if (position < 0)
            return data[addPosition+position];
        else if (position >= data.length)
            return 0;
        else
            return data[position];
    }
    public void put(int position, byte value) {
        // Store the value in the specified position in the array.
        // The data array will increase in size to include this
        // position, if necessary.
        if (position >= data.length) {
            // The specified position is outside the actual size of
            // the data array.  Add enough bytes.
            int newSize = position+1;
            byte[] newData = new byte[newSize];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
            addPosition = position;
            // The following line is for demonstration purposes only.
//            System.out.println("Size of dynamic array increased to "+ newSize);
        }
        data[position] = value;
    }
    public void add(byte value) {
        // Store the value in the current position in the array.
        // The data array will increase in size to include this
        // position, if necessary.
        if (addPosition == data.length) {
            // The specified position is outside the actual size of
            // the data array.  Double the size.
            int newSize = 2 * data.length;
            byte[] newData = new byte[newSize];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
            // The following line is for demonstration purposes only.
//            System.out.println("Size of dynamic array increased to "+ newSize);
        }
        data[addPosition++] = value;
    }
    public byte[] getBytes() {
        byte bs[] = new byte[addPosition];
        System.arraycopy(data, 0, bs, 0, addPosition);
        return bs;
    }
    public int size() {
        return addPosition;
    }
} // end class DynamicArrayOfByte
