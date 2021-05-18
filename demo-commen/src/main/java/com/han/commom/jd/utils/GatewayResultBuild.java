// package com.jd.jxpp.scm.opencity.common.response;
//
//
// import java.util.List;
//
// /**
//  * @program: platform-operation
//  * @description:
//  * @author: yangh
//  * @create: 2020-04-07 14:45
//  **/
// public class GatewayResultBuild {
//
//     /**
//      * 包装成功结果
//      * @return
//      */
//     public static <T> GatewayResult<T> wrapSuccess(T data) {
//         GatewayResult<T> gatewayResult = wrapResult(GatewayErrorCodeEnum.SUCCESS);
//         gatewayResult.setData(data);
//         return gatewayResult;
//     }
//
//     public static GatewayResult<String> wrapSuccess() {
//         GatewayResult<String> gatewayResult = wrapResult(GatewayErrorCodeEnum.SUCCESS);
//         gatewayResult.setData(GatewayErrorCodeEnum.SUCCESS.getCodeDesc());
//         return gatewayResult;
//     }
//
//
//     public static <T extends List<?>> GatewayResult<T> wrapSuccess(T data, Integer pageIndex, Integer pageSize, Long total) {
//         GatewayDataResult<T> dataResult = new GatewayDataResult<>();
//         if (pageIndex != null) {
//             dataResult.setCurrent(pageIndex);
//         }
//
//         if (pageSize != null) {
//             dataResult.setPageSize(pageSize);
//         }
//
//         if (total != null) {
//             dataResult.setTotal(total);
//         }
//         dataResult.setData(data);
//         GatewayResult<T> gatewayResult = wrapResult(GatewayErrorCodeEnum.SUCCESS);
//         gatewayResult.setData(dataResult);
//         return gatewayResult;
//     }
//
//
//     /**
//      * 包装返回结果
//      * @param errorCode
//      * @return
//      */
//     public static <T> GatewayResult<T> wrapResult(GatewayErrorCodeEnum errorCode) {
//         GatewayResult<T> result = new GatewayResult<>();
//         result.setSuccess(errorCode.isSuccess());
//         result.setCode(errorCode.getGwCode());
//         result.setMessage(errorCode.getCodeDesc());
//         return result;
//     }
//
//     public static <T> GatewayResult<T> wrapErrorResult(Integer code, String message) {
//         GatewayResult<T> result = new GatewayResult<>();
//         result.setSuccess(ResultConstant.FAILED);
//         result.setCode(code);
//         result.setMessage(message);
//         return result;
//     }
//
//     public static <T> GatewayResult<T> wrapErrorResult(String message) {
//         GatewayResult<T> result = new GatewayResult<>();
//         result.setSuccess(ResultConstant.FAILED);
//         result.setCode(GatewayErrorCodeEnum.ERROR_E0001.getGwCode());
//         result.setMessage(message);
//         return result;
//     }
//
//     public static GatewayResult<?> wrapResult(Boolean success, Integer code, String message) {
//         GatewayResult<?> result = new GatewayResult<>();
//         result.setSuccess(success);
//         result.setCode(code);
//         result.setMessage(message);
//         return result;
//     }
// }
