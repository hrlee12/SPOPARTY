import axios from 'axios'
import { httpStatusCode } from '@/util/http-status'

const {VITE_REST_API} = import.meta.env;

function localAxios() {
    console.log("히히 API_URL 발사 -> ", VITE_REST_API)
    // AccessToken 자동 발급해주고 싶어
    const instance = axios.create({
        baseURL:`${VITE_REST_API}`,
        headers: {
            "Content-Type" : "application/json;charset=utf-8",
        },
    });

    // request보내기 전에 인터셉터가 채감
    // 토큰이 있다면 헤더에 토큰을 넣어준다
    instance.interceptors.request.use(
        (request) => {
            let token = localStorage.getItem("accessToken")
            if (token) request.headers.Authorization = token;
            return request;
        }, 
        (error) => {
            console.log(error);
            return Promise.reject(error);
        }
    )

    let isTokenRefreshing = false;

    // response를 받은 직후 실행되는 interceptor
    // accessToken이 옳게 되지 않은 경우
    // refreshToken을 이용하여 
    instance.interceptors.response.use(
        (response) => {
            return response;
        },
        async (error) => {
            const {
                config,
                response : {status},
            } = error;

            // 페이지가 새로고침되어 저장된 accessToken이 없어진 경우
            // or 토큰 자체가 만료
            if (status == httpStatusCode.UNAUTHORIZED) {
                const originalRequest = config;

                // Token을 재발급하는 동안 다른 요청은 대기
                // Why? -> 다른 요청이 들어오면 새로 받은 Access Token이 유효X
                if (!isTokenRefreshing) {
                    isTokenRefreshing = true;

                    // 에러가 발생했던 컴포넌트의 axios로 이동하고 싶으면 
                    // 반드시 return 붙여라
                    return await instance.post("/authentication/regenerate")
                        .then((response) => {
                            const newAccessToken = response.data.Authorization;

                            instance.defaults.headers.common["Authorization"] = newAccessToken;
                            originalRequest.headers.Authorization = newAccessToken;

                            isTokenRefreshing = false;

                            // 에러가 발생했던 원래 요청 반환
                            return instance(originalRequest);
                        })
                }
            }
            else if (status === httpStatusCode.FORBIDDEN) {
                alert(error.response.data.message)
            }
            return Promise.reject(error);
        }
    );
    return instance

}

export {localAxios}