package com.han.commom.util;// package com.jd.jxpp.scm.opencity.utils;
//
// import org.springframework.util.CollectionUtils;
//
// import java.util.Collections;
// import java.util.List;
// import java.util.Objects;
// import java.util.stream.Collectors;
//
// /**
//  * @Author : wangjingwang
//  * @Date : 2020/4/7 10:22
//  * @Description :
//  */
// public class Converter<DO, BO, DTO> {
//     public Out<DO, BO, DTO> out;
//     public In<DO, BO, DTO> in;
//
//     public static <DO, BO, DTO> Converter<DO, BO, DTO> create(Class<DO> doClass, Class<BO> boClass, Class<DTO> dtoClass,
//                                                               String... ignoreProperties) {
//         Converter<DO, BO, DTO> converter = new Converter<>();
//         converter.in = createIn(doClass, boClass, dtoClass, ignoreProperties);
//         converter.out = createOut(doClass, boClass, dtoClass, ignoreProperties);
//         return converter;
//     }
//
//     public static <DO, BO, DTO> In<DO, BO, DTO> createIn(Class<DO> doClass, Class<BO> boClass, Class<DTO> dtoClass,
//                                                               String... ignoreProperties) {
//         return new In<>(doClass, boClass, dtoClass, ignoreProperties);
//     }
//
//     public static <DO, BO, DTO> Out<DO, BO, DTO> createOut(Class<DO> doClass, Class<BO> boClass, Class<DTO> dtoClass,
//                                                                    String... ignoreProperties) {
//         return new Out<>(doClass, boClass, dtoClass, ignoreProperties);
//     }
//
//     public static class Empty {
//
//     }
//
//     public static class Out<DO, BO, DTO> {
//         private Class<BO> boClass;
//         private Class<DTO> dtoClass;
//
//         private CopyUtils.Copier<DO, BO> toBO;
//         private CopyUtils.Copier<BO, DTO> toDTO;
//
//         public Out(Class<DO> doClass, Class<BO> boClass, Class<DTO> dtoClass,
//                    String... ignoreProperties) {
//             this.boClass = boClass;
//             this.dtoClass = dtoClass;
//             this.toBO = CopyUtils.createCopier(doClass, boClass, ignoreProperties);
//             this.toDTO = CopyUtils.createCopier(boClass, dtoClass, ignoreProperties);
//         }
//
//         public BO toBO(DO s) {
//             if (null == s) {
//                 return null;
//             }
//             BO t = instance(boClass);
//             toBO.copy(s, t);
//             return t;
//         }
//
//         public List<BO> toBOList(List<DO> srcList) {
//             if (CollectionUtils.isEmpty(srcList)) {
//                 return Collections.emptyList();
//             }
//             return srcList.stream()
//                     .map(this::toBO)
//                     .filter(Objects::nonNull)
//                     .collect(Collectors.toList());
//         }
//
//         public DTO toDTO(BO s) {
//             if (null == s) {
//                 return null;
//             }
//             DTO t = instance(dtoClass);
//             toDTO.copy(s, t);
//             return t;
//         }
//
//         public List<DTO> toDTOList(List<BO> srcList) {
//             if (CollectionUtils.isEmpty(srcList)) {
//                 return Collections.emptyList();
//             }
//             return srcList.stream()
//                     .map(this::toDTO)
//                     .filter(Objects::nonNull)
//                     .collect(Collectors.toList());
//         }
//     }
//
//     public static class In<DO, BO, DTO> {
//         private Class<DO> doClass;
//         private Class<BO> boClass;
//         private CopyUtils.Copier<DTO, BO> toBO;
//         private CopyUtils.Copier<BO, DO> toDO;
//
//         public In(Class<DO> doClass, Class<BO> boClass, Class<DTO> dtoClass,
//                    String... ignoreProperties) {
//             this.doClass = doClass;
//             this.boClass = boClass;
//             this.toBO = CopyUtils.createCopier(dtoClass, boClass, ignoreProperties);
//             this.toDO = CopyUtils.createCopier(boClass, doClass, ignoreProperties);
//         }
//
//         public BO toBO(DTO s) {
//             if (null == s) {
//                 return null;
//             }
//             BO t = instance(boClass);
//             toBO.copy(s, t);
//             return t;
//         }
//
//         public List<BO> toBOList(List<DTO> srcList) {
//             if (CollectionUtils.isEmpty(srcList)) {
//                 return Collections.emptyList();
//             }
//             return srcList.stream()
//                     .map(this::toBO)
//                     .filter(Objects::nonNull)
//                     .collect(Collectors.toList());
//         }
//
//         public DO toDO(BO s) {
//             if (null == s) {
//                 return null;
//             }
//             DO t = instance(doClass);
//             toDO.copy(s, t);
//             return t;
//         }
//
//         public List<DO> toDOList(List<BO> srcList) {
//             if (CollectionUtils.isEmpty(srcList)) {
//                 return Collections.emptyList();
//             }
//             return srcList.stream()
//                     .map(this::toDO)
//                     .filter(Objects::nonNull)
//                     .collect(Collectors.toList());
//         }
//     }
//
//     private static <T> T instance(Class<T> tClass) {
//         try {
//             return tClass.newInstance();
//         } catch (InstantiationException | IllegalAccessException e) {
//             throw new RuntimeException(String.format("%s instance error", tClass), e);
//         }
//     }
// }
