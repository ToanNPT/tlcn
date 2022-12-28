import React from "react";
import { Form } from "react-bootstrap";
import { FaUserAlt } from "react-icons/fa";
export default function ContactPage() {
  return (
    <div className="contact__container">
      <div className="contact__inner container">
        <div className="row">
          <div className="col-12 col-md-6">
            <div className="contact__left">
              <lottie-player
                src="https://assets4.lottiefiles.com/packages/lf20_ksklhijl.json"
                background="transparent"
                speed="1.5"
                loop
                autoplay
              ></lottie-player>
            </div>
          </div>
          <div className="col-12 col-md-6">
            <div className="contact__right d-flex flex-column justify-content-center align-items-center h-100">
              <h1 className="contact__title mb-5">Kết nối với LEGACY</h1>
              <Form className="w-100">
                <Form.Group className="mb-4 custom__input">
                  <i class="bx bx-user"></i>
                  <Form.Control type="text" placeholder="Name" />
                </Form.Group>
                <Form.Group className="mb-4  custom__input">
                  <i class="bx bx-message-square-dots"></i>
                  <Form.Control
                    as="textarea"
                    type="text"
                    placeholder="Nhập nội dung"
                  />
                </Form.Group>
              </Form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
