import React from "react";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import BackToTop from "../components/BackToTop/BackToTop";
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
