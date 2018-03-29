package com.yuanben.examples;

import com.yuanben.common.InvalidException;
import com.yuanben.crypto.HexUtil;
import com.yuanben.model.SecretKey;
import com.yuanben.service.ECKeyProcessor;

/**
 * ECKeyProcessor Test
 */
public class KeyTest {

    private static String private_key = "3c4dbee4485557edce3c8878be34373c1a41d955f38d977cfba373642983ce4c";
    private static String public_key = "03d75b59a801f6db4bbb501ff8b88743902aa83a3e54237edcd532716fd27dea77";
    private static String content = "原本链是一个分布式的底层数据网络；" +
            "原本链是一个高效的，安全的，易用的，易扩展的，全球性质的，企业级的可信联盟链；" +
            "原本链通过智能合约系统以及数字加密算法，实现了链上数据可持续性交互以及数据传输的安全；" +
            "原本链通过高度抽象的“DTCP协议”与世界上独一无二的“原本DNA”互锁，确保链上数据100%不可篡改；" +
            "原本链通过优化设计后的共识机制和独创的“闪电DNA”算法，已将区块写入速度提高至毫秒级别";
    private static String sign_msg = "b7a59601d0a45ff33c93a61709fbc7586afbb952efb7eed19b348e44caa1fdbd6fbb963d4cb2fd58a128e5831a6f05e05e5064b12cfb3e44842b98a6abb2841c00";

    //examples result:
    //private_key:3c4dbee4485557edce3c8878be34373c1a41d955f38d977cfba373642983ce4c
    //public_key: 03d75b59a801f6db4bbb501ff8b88743902aa83a3e54237edcd532716fd27dea77
    private static void GeneratorSecp256k1KeyTest() {
        SecretKey secretKey = ECKeyProcessor.GeneratorSecp256k1Key();
        System.out.println("private_key:" + secretKey.getPrivateKey() +
                " \npublic_key: " + secretKey.getPublicKey());
    }

    //examples result:
    //03d75b59a801f6db4bbb501ff8b88743902aa83a3e54237edcd532716fd27dea77
    private static void GetPubKeyFromPriTest() {

        try {
            private_key = "E28D61220ADBBEFC0BE9421570C3B77BC24CA33C18CB62FCCFBC982C40B8F888A59A0F38301CF02E82666541D78D6981C5A6481D9AD25EC933FC9E2FB97C29FC";
            String pubKeyFromPri = ECKeyProcessor.GetPubKeyFromPri(private_key);
            System.out.println(pubKeyFromPri);
        } catch (InvalidException e) {
            e.printStackTrace();
        }
    }

    //examples result:
    //b7a59601d0a45ff33c93a61709fbc7586afbb952efb7eed19b348e44caa1fdbd6fbb963d4cb2fd58a128e5831a6f05e05e5064b12cfb3e44842b98a6abb2841c00
    private static void SignTest() {
        try {
            String sign = ECKeyProcessor.Sign(private_key, content.getBytes());
            System.out.println(sign);
        } catch (InvalidException e) {
            e.printStackTrace();
        }

    }

    //examples result:
    //true
    private static void VerifySignatureTest() {
        try {
            boolean b = ECKeyProcessor.VerifySignature(public_key, sign_msg, ECKeyProcessor.Keccak256(content));
            System.out.println(b);
        } catch (InvalidException e) {
            e.printStackTrace();
        }
    }

    //examples result:
    //54ce1d0eb4759bae08f31d00095368b239af91d0dbb51f233092b65788f2a526
    private static void Keccak256Test() {
        System.out.println(HexUtil.bytesToHex(ECKeyProcessor.Keccak256(content)));
    }


    public static void main(String[] args) {

        GetPubKeyFromPriTest();
//        Keccak256Test();
    }
}
