import React from "react";
import Banner from "../components/Banner/Banner";
import Footer from "../layouts/Footer/Footer";
import Header from "../layouts/Header/Header";
import MainRoute from "../MainRoute/MainRoute";

export default function CustomLayout() {
  return (
    <>
      <Header />
      <MainRoute />
      <Footer />
    </>
  );
}