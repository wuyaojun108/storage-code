<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <!--分页模块-->
        <div id="page_nav">
        <!--如果是第一页，不展示首页和上一页-->
        <c:if test="${page.pageNo > 1}">
            <a href="${page.uriPart}&pageNo=1">首页</a>
            <a href="${page.uriPart}&pageNo=${requestScope.page.pageNo - 1}">上一页</a>
        </c:if>

        <%--分页条页码输出。要求显示5个连续的页码，当前页码在中间，除了当前页码，每个页码都可以跳转--%>
        <c:choose>
            <%-- 第一种情况：当总页数小于等于5的时候 --%>
            <c:when test="${page.pageTotal <= 5}">
                <c:set var="begin" value="1"></c:set>
                <c:set var="end" value="${page.pageTotal}"></c:set>
            </c:when>
            <%-- 第二种情况：当总页数大于5的时候 --%>
            <c:when test="${page.pageTotal > 5}">
                <c:choose>
                    <%-- 情况2.1：当页码是前面3个：1，2，3，页码范围是1-5 --%>
                    <c:when test="${page.pageNo <= 3}">
                        <c:set var="begin" value="1"></c:set>
                        <c:set var="end" value="5"></c:set>
                    </c:when>
                    <%-- 情况2.2：当页码是最后3个，页码范围是倒数第五个到倒数第一个--%>
                    <c:when test="${page.pageNo > page.pageTotal - 3}">
                        <c:set var="begin" value="${page.pageTotal - 4}"></c:set>
                        <c:set var="end" value="${page.pageTotal}"></c:set>
                    </c:when>
                    <%-- 情况2.3：页码范围是当前页码减2到当前页码加2--%>
                    <c:otherwise>
                        <c:set var="begin" value="${page.pageNo - 2}"></c:set>
                        <c:set var="end" value="${page.pageNo + 2}"></c:set>
                    </c:otherwise>
                </c:choose>
            </c:when>

        </c:choose>

        <%--循环输出分页条码--%>
        <c:forEach begin="${begin}" end="${end}" var="i">
            <c:if test="${i == page.pageNo}">
                【${i}】
            </c:if>
            <c:if test="${i != page.pageNo}">
                <a href="${page.uriPart}&pageNo=${i}">${i}</a>
            </c:if>
        </c:forEach>

        <!--如果是最后一页，不展示下一页和末页-->
        <c:if test="${page.pageNo < page.pageTotal}">
            <a href="${page.uriPart}&pageNo=${requestScope.page.pageNo + 1}">下一页</a>
            <a href="${page.uriPart}&pageNo=${requestScope.page.pageTotal}">末页</a>
        </c:if>

        共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
        到第<input value="${requestScope.page.pageNo}" name="pn" id="pn_input"/>页
        <input id="jumpTo" type="button" value="确定">
    </div>
	</div>
	<script src="static/scripts/jquery-3.6.0.js" ></script>
    <script>
        $(function(){
            // 实现跳到指定页面的功能
            $("#jumpTo").click(function(){
                var pageNo = $("#pn_input").val()
                location.href = "${page.uriPart}&pageNo=" + pageNo
            })
        })
    </script>