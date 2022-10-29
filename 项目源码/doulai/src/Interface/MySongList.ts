import Search from "@/Interface/Search";

export default interface MySongList {
    user_id: number;
    pageCount: number;
    currentPage: number;
    searchList: Array<Search>;
}