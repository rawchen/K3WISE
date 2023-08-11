package com.lundong.k3wise.event;

import com.lark.oapi.card.CardActionHandler;
import com.lark.oapi.core.request.EventReq;
import com.lark.oapi.core.response.EventResp;
import com.lark.oapi.sdk.servlet.ext.HttpTranslator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet的适配器，用于适配基于Servlet技术栈实现的Web服务
 */
public class CustomServletAdapter {

  private static final HttpTranslator HTTP_TRANSLATOR = new HttpTranslator();

  /**
   * 处理消息事件
   *
   * @param req
   * @param response
   * @param eventDispatcher
   * @throws Throwable
   */
  public void handleEvent(HttpServletRequest req, HttpServletResponse response,
      CustomEventDispatcher eventDispatcher) throws Throwable {
    // 转换请求对象
    EventReq eventReq = HTTP_TRANSLATOR.translate(req);

    // 处理请求
    EventResp resp = eventDispatcher.handle(eventReq);

    // 回写结果
    HTTP_TRANSLATOR.write(response, resp);
  }

  /**
   * 处理卡片消息
   *
   * @param req
   * @param response
   * @param handler
   * @throws Throwable
   */
  public void handleCardAction(HttpServletRequest req, HttpServletResponse response,
      CardActionHandler handler) throws Throwable {
    // 转换请求对象
    EventReq eventReq = HTTP_TRANSLATOR.translate(req);

    // 处理请求
    EventResp resp = handler.handle(eventReq);

    // 回写结果
    HTTP_TRANSLATOR.write(response, resp);
  }
}
