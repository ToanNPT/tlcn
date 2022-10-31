import { createSlice } from "@reduxjs/toolkit";
import Swal from "sweetalert2";
import cartApi, { AddToCartAction } from "../api/cartApi";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    listCart: [],
    isFetching: false,
    totalQuantity: 0,
    totalPrice: 0,
  },
  reducers: {
    getListCart: (state, action) => {
      state.listCart = action.payload.cartDetailList;

      state.totalPrice = action.payload.totalPrice;
    },
    deleteCart: (state, action) => {
      console.log(action.payload);
      state.listCart = state.listCart.filter(
        (item) => item.id !== action.payload
      );
      state.totalQuantity = state.totalQuantity - 1;
    },
    insertCart: (state, action) => {
      state.listCart.push(action.payload);
      state.totalQuantity += 1;
      state.totalPrice += action.payload.price;
    },
  },
});

export const handleAddtoCart = async (
  courseId,
  username,
  dispatch,
  listCart
) => {
  //Kiểm tra xem có trong giỏ hàng chưa
  const checkCart = listCart.find((item) => item.id === courseId);
  if (checkCart) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "This course is already in your cart!",
    });
  } else {
    const res = await cartApi.AddToCartAction(courseId, username);
    if (res.errorCode === "") {
      dispatch(insertCart(res.data.course));
      Swal.fire({
        icon: "success",
        title: "Thêm vào giỏ hàng thành công",
        showConfirmButton: false,
        timer: 1500,
      });
    } else {
      Swal.fire({
        icon: "error",
        title: "Thêm vào giỏ hàng thất bại",
        showConfirmButton: false,
        timer: 1500,
      });
    }
  }
};

export const handleDeleteFromCart = async (
  cartDetailId,
  username,
  dispatch
) => {
  const res = await cartApi.DeleteFormCart(cartDetailId, username);
  if (res.errorCode === "") {
    dispatch(deleteCart(res.data.id));
    Swal.fire({
      icon: "success",
      title: "Xóa khỏi giỏ hàng thành công",
      showConfirmButton: false,
      timer: 1500,
    });
  } else {
    Swal.fire({
      icon: "error",
      title: "Xóa khỏi giỏ hàng thất bại",
      showConfirmButton: false,
      timer: 1500,
    });
  }
};
export const {
  getListCart,
  deleteCart,
  updateCart,
  insertCart,
  setTotalPrice,
  setTotalQuantity,
  setFetching,
} = cartSlice.actions;
export default cartSlice.reducer;