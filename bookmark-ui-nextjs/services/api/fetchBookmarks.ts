import { BookmarksResponse } from '@/app/types/bookmark';
import axios from 'axios';
import React from 'react'

const API_BASE_URL = "http://localhost:8080";


// 전제조회 + Page 조회 + 검색어 조회 +  (Page조회+검색어 조회)
// TypeScript는 javascript에서 타입이 정해지지 않는 동적타입을 사용하지 매핑되는 타입을 지정한다
// 타입이 지정되어 있기 때문에 사용시 개발의 정확성을 높힐 수 있다(undefinded가 발생하지 않는다)
export const fetchBookmarks = async (page:number, query?:string): Promise<BookmarksResponse> => {
    const resp = await axios.get<BookmarksResponse>(`${API_BASE_URL}/api/bookmarks?page=${page}&query=${query}`);
    console.log(resp.data)
    return resp.data;
}

// export async function fetchBookmarkss(page:number, query:string): Promise<BookmarksResponse>{
//     const resp = await fetch(`http://localhost:8080/api/bookmarks?page=${page}&query=${query}`);
//     if(!resp.ok){
//         throw new Error ('잘못된 결과');
//     }
//     return resp.json();
// }


// 입력 ${API_BASE_URL}/api/bookmarks , POST, CreateBookmarkRequest.java(JSON) 
// => 예외(validation처리 -> HTML -> @ControllerAdvice -> 선택Exception ->JSON message, Status, filed)
                                                                        /*
                                                                          {
                                                                            "field": "url",
                                                                            "message": "URL은 필수 입력값 입니다",
                                                                            "status": 400
                                                                          } 
                                                                        */
export const saveBookmark = async(bookmark : {title:string, url:String}) =>{
    try{
        const resp = await axios.post(`${API_BASE_URL}/api/bookmarks`, bookmark);
        //Promise 객체는 data에 값을 담아서 온다
        return resp.data;
    }catch(error){
        // 에러가 발생 했을 경우 처리 메시지
        console.log("Error Saving Bookmark:", error);
        throw new Error("북마크 저장에 실패하였습니다"); // 오류를 throw하여 호출자에게 전달
    }
}
