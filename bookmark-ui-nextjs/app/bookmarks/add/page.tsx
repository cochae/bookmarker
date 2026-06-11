'use client' // Client Component 작성법에서 Server Component 작성방법을 적용할 때 선언한다
import { saveBookmark } from '@/services/api/fetchBookmarks';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react'

const page = () => {

  const router = useRouter();

  // 서버에 전송할 값
  const [title, setTitle] = useState("");
  const [url, setUrl] = useState("");

  // 서버에서 오류가 발생하면 출력할 값
  const [message, setMessage] = useState<String | null>(null);

  /*
   js에서는 any타입이지만 typescript에서는 타입이 있어야 한다.
   form에 관련된 이벤트를 구체적으로 처리 하기 위해서 event를 받는다 => syntecticEvent 로 처리 한다
  */
  const handleSubmit = (e:React.SyntheticEvent<HTMLFormElement>)=>{
    e.preventDefault();
    //서버호출을 해줌

    //유효성 검사
    if(!url){ // 값이 존재 여부
      alert("url이 필수 값입니다")
      return;
    }

    const payload = {
      title,
      url
    }

    try{
      saveBookmark(payload)
        .then(response =>{
          console.log("Save Bookmark 응답 : ", response);
          setTitle("");
          setUrl("");
          // 저장 성공 메시지
          setMessage("새로운 Bookmark가 저장되었습니다")

          setTimeout(()=>{
            router.push("/bookmarks");
          }, 1000);
        }) 
        
        .catch(error =>{
          setMessage(error.message || "새로운 Bookmark의 저장에 실패하였습니다");
        })

    }catch(error){ // Promise에서 reject되었을 경우
      setMessage("새로운 Bookmark의 저장에 실패하였습니다")
    }
  }

  return (
    <div>
      {message && <div className='alert alert-primary' role="alert">{message}</div>}
      <form onSubmit={e => handleSubmit(e)}>
        <fieldset>
          <legend>새로운 Bookmark 등록</legend>
          <div className='mb-3'>
            <label htmlFor='title' className='form-label'>제목</label>
            <input type='text' id="title" className='form-control' placeholder='Title' 
                  value={title} onChange={e => setTitle(e.target.value)}/>
          </div>    
          <div className='mb-3'>
            <label htmlFor='url' className='form-label'>링크</label>
            <input type='text' id="url" className='form-control' placeholder='Url' 
                  value={url} onChange={e => setUrl(e.target.value)}/>
          </div> 

          <div className='d-grid gap-2 col-6 mx-auto'>
            <button type='submit' className='btn btn-primary'>Submit</button>
          </div>

        </fieldset>
      </form>

    </div>
  )
}

export default page