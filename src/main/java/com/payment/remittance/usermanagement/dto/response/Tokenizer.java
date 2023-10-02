package com.payment.remittance.usermanagement.dto.response;

import lombok.Data;

import java.util.StringTokenizer;
import java.util.Vector;

@Data
public class Tokenizer {

    public static String[] tokenize(String input, String delim) {
        Vector v = new Vector();
        StringTokenizer t;
        //System.out.println("...TOKENIZE::" + input + "    " + delim);
        if (delim.equals("default")) {
            t = new StringTokenizer(input);
        } else {
            t = new StringTokenizer(input, delim);
        }
        for (; t.hasMoreTokens(); v.addElement(t.nextToken()));
        String[] cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++) {
            cmd[i] = (String) v.elementAt(i);
            //System.out.println("...TOKENIZE CMD::" + cmd[i]);
        }

        return cmd;
    }
}
