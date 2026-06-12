"use client";
import { useRouter } from "next/navigation";
// 클라이언트 컴포넌트에서 비동기 작업을 직접적으로 수행 할수는 없다
// useEffect 또는 이벤트 핸들러에서 async/await 를 사용할 수 있다.
// useState, useEffect, useRouter 등 브라우저의 전용 훅이 사용가능하다
import React, { useState } from "react";

const SearchForm = () => {
  const router = useRouter();
  const [query, setQuery] = useState("");

  // event:React.SyntheticEvent 는 모든 타입을 포함하는 상위 이벤트 타입 => typescript 타입이 없으면 사용하지 못함
  const handleSearch = async (e: React.SyntheticEvent) => {
    e.preventDefault();
    if (query == "") {
      router.push("/bookmarks");
      return;
    }
    router.push(`/bookmarks?page=1&query=${query}`); //서버 컴포넌트에서 사용 불가
  };

  return (
    <div className="pb-4">
      <form className="d-flex" role="search" onSubmit={handleSearch}>
        <input
          className="form-control me-2"
          type="search"
          placeholder="Search"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button className="btn btn-outline-success" type="submit">
          Search
        </button>
      </form>
    </div>
  );
};

export default SearchForm;
