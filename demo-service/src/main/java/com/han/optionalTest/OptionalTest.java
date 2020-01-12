// package com.han.optionalTest;
//
// import com.han.commom.User;
// import com.han.commom.ValueAbsentException;
//
// import java.util.Optional;
//
// /**
//  * @Author: hanyf
//  * @Description:
//  * @Date: Created by in 14:50 2018/3/6
//  */
// public class OptionalTest {
//
//     public void test() {
//         User user = new User();
//         user.setName("积分你的是");
//         User user2 = new User();
//         user2.setName("欧冠");
//         Optional<User> optional = Optional.of(user);
//         System.out.println(optional);
//         System.out.println(optional.isPresent());
//         System.out.println(optional.get());
//         Optional<User> optionalNull = Optional.ofNullable(null);
//         System.out.println(optionalNull);
//         optional.ifPresent((value) -> {
//             System.out.println("The length of the value is: " + value.getName().length());
//             System.out.println("The length of the value is: " + value);
//         });
//         optionalNull.ifPresent((value) -> {
//             System.out.println("The length of the value is: " + value.getName().length());
//             System.out.println("The length of the value is: " + value);
//         });
//
//         System.out.println(optional.orElse(user));
//         System.out.println(optionalNull.orElse(null));
//
//         System.out.println(optional.orElseGet(() -> user2));
//         System.out.println(optionalNull.orElseGet(() -> user2));
//
//         try {
//             System.out.println(optional.orElseThrow(ValueAbsentException::new));
//         } catch (ValueAbsentException e) {
//             System.out.println(e.getMessage());
//         }
//
//         try {
//             System.out.println(optionalNull.orElseThrow(ValueAbsentException::new));
//         } catch (ValueAbsentException e) {
//             System.out.println(e.getMessage());
//         }
//         Optional<String> name = Optional.of("dskaffgdsj");
//         Optional<String> upperName = name.map((value) -> value.toUpperCase());
//         System.out.println(upperName);
//         System.out.println(upperName.orElse("No value found"));
//
//         Optional<String> name1 = Optional.ofNullable(null);
//         Optional<String> upperName1 = name1.map((value) -> value.toUpperCase());
//         System.out.println(upperName1.orElse("No value found"));
//
//         upperName = optional.flatMap((value) -> Optional.of(value.getName().toUpperCase()));
//         System.out.println(upperName.orElse("No value found"));//输出SANAULLA
//
//         Optional<String> longName = name.filter((value) -> value.length() > 6);
//         System.out.println(longName.orElse("The name is less than 6 characters"));//输出Sanaulla
//
//         Optional<String> anotherName = Optional.of("Sana");
//         Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
//         System.out.println(shortName);
//         System.out.println(shortName.orElse("The name is less than 6 characters"));
//     }
//
//     public static void main(String[] args) {
//         OptionalTest optionalTest = new OptionalTest();
//         optionalTest.test();
//     }
// }
