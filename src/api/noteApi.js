import axiosClient from "./axiosClient";

const noteApi = {
  getnotebyvideoid(username, videoid) {
    const url = `/note/${username}/${videoid}`;
    return axiosClient.get(url);
  },
  addnote(data) {
    const url = `/note/add`;
    return axiosClient.post(url, data);
  },
  deletenotebyid(id) {
    const url = `/note/delete/${id}`;
    return axiosClient.delete(url);
  },
};

export default noteApi;
