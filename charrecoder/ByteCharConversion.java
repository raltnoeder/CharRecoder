package charrecoder;

/**
 * Copyright (C) 2021 Robert ALTNOEDER
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that
 * the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Robert Altnoeder &lt;r.altnoeder@gmx.net&gt;
 */
public class ByteCharConversion
{
    public static char[] rawBytesToChars(byte[] input)
    {
        char[] output = new char[input.length];
        for (int idx = 0; idx < output.length; ++idx)
        {
            output[idx] = (char) input[idx];
        }
        return output;
    }

    public static class BigEndian
    {
        public static char[] rawBytePairsToChars(byte[] input)
        {
            if ((input.length & 1) == 1)
            {
                throw new IllegalArgumentException();
            }

            char[] output = new char[input.length >>> 1];
            for (int outIdx = 0; outIdx < output.length; ++outIdx)
            {
                int inIdx = outIdx >>> 1;
                output[outIdx] = (char) (input[inIdx] << 8);
                ++inIdx;
                output[outIdx] |= (char) (input[inIdx] & 0xFF);
            }
            return output;
        }

        public static byte[] rawCharsToBytePairs(char[] input)
        {
            if (input.length > (Integer.MAX_VALUE >>> 1))
            {
                throw new IllegalArgumentException();
            }

            byte[] output = new byte[input.length << 1];
            for (int inIdx = 0; inIdx < input.length; ++inIdx)
            {
                int outIdx = (inIdx << 1);
                output[outIdx] = (byte) ((input[inIdx] & 0xFF00) >>> 8);
                ++outIdx;
                output[outIdx] = (byte) (input[inIdx] & 0xFF);
            }

            return output;
        }
    }

    public static class LittleEndian
    {
        public static char[] rawBytePairsToChars(byte[] input)
        {
            if ((input.length & 1) == 1)
            {
                throw new IllegalArgumentException();
            }

            char[] output = new char[input.length >>> 1];
            for (int outIdx = 0; outIdx < output.length; ++outIdx)
            {
                int inIdx = outIdx >>> 1;
                output[outIdx] = (char) (input[inIdx] & 0xFF);
                ++inIdx;
                output[outIdx] |= (char) (input[inIdx] << 8);
            }
            return output;
        }

        public static byte[] rawCharsToBytePairs(char[] input)
        {
            if (input.length > (Integer.MAX_VALUE >>> 1))
            {
                throw new IllegalArgumentException();
            }

            byte[] output = new byte[input.length << 1];
            for (int inIdx = 0; inIdx < input.length; ++inIdx)
            {
                int outIdx = (inIdx << 1);
                output[outIdx] = (byte) (input[inIdx] & 0xFF);
                ++outIdx;
                output[outIdx] = (byte) ((input[inIdx] & 0xFF00) >>> 8);
            }

            return output;
        }
    }
}
