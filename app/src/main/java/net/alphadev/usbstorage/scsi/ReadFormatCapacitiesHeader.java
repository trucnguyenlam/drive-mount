package net.alphadev.usbstorage.scsi;

import java.util.BitSet;

import static net.alphadev.usbstorage.util.BitStitching.convertToInt;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
@SuppressWarnings("unused")
public class ReadFormatCapacitiesHeader {
    public static final int LENGTH = 12;

    private byte mCapacityListLength;
    private int mNumberOfBlocks;
    private DescriptorTypes mDescriptorTypes;
    private int mBlockLength;

    public ReadFormatCapacitiesHeader(byte[] answer) {
        /** first three bits are reserved **/
        mCapacityListLength = answer[3];
        mNumberOfBlocks = convertToInt(answer, 4);

        BitSet typeSet = BitSet.valueOf(new byte[]{answer[5]});
        if (typeSet.get(0)) {
            if (typeSet.get(1)) {
                mDescriptorTypes = DescriptorTypes.NO_MEDIA_PRESENT;
            } else {
                mDescriptorTypes = DescriptorTypes.FORMATTED_MEDIA;
            }
        } else {
            if (typeSet.get(1)) {
                mDescriptorTypes = DescriptorTypes.UNFORMATTED_MEDIA;
            } else {
                mDescriptorTypes = DescriptorTypes.RESERVED;
            }
        }

        byte[] tempBlockLength = new byte[]{
                0, answer[9], answer[10], answer[11],
        };

        mBlockLength = convertToInt(tempBlockLength, 0);
    }

    public int getCapacityEntryCount() {
        return mCapacityListLength;
    }

    public int getNumberOfBlocks() {
        return mNumberOfBlocks;
    }

    public DescriptorTypes getDescriptorTypes() {
        return mDescriptorTypes;
    }

    public int getBlockLength() {
        return mBlockLength;
    }

    public static enum DescriptorTypes {
        RESERVED,
        UNFORMATTED_MEDIA,
        FORMATTED_MEDIA,
        NO_MEDIA_PRESENT
    }
}