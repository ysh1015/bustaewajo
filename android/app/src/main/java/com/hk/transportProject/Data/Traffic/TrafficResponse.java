package com.hk.transportProject.Data.Traffic;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "response", strict = false)
public class TrafficResponse
{
    @Element(name = "comMsgHeader")
    public comMsgHeader comMsgHeader;

    @Element(name = "msgHeader")
    public msgHeader msgHeader;

    @Element(name = "msgBody")
    public msgBody msgBody;

    public static class msgHeader {
        @Element(name = "queryTime")
        public String queryTime;

        @Element(name = "resultCode")
        public String resultCode;

        @Element(name = "resultMessage")
        public String resultMessage;
    }

    public static class comMsgHeader {    }

    public static class msgBody {
        @Element(name = "busStationAroundList")
        public busstationAroundList busstationAroundList;
    }

    public static class busstationAroundList {

        @Element(name = "centerYn")
        public String centerYn;

        @Element(name = "districtCd")
        public String districtCd;

        @Element(name = "mobileNo")
        public String mobileNo;

        @Element(name = "regionName")
        public String regionName;

        @Element(name = "stationId")
        public String stationId;

        @Element(name = "stationName")
        public String stationName;

        @Element(name = "x")
        public String x;

        @Element(name = "y")
        public String y;

        @Element(name = "distance")
        public String distance;
    }


}




/*
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<response>
    <comMsgHeader/>
    <msgHeader>
        <queryTime>2024-10-21 23:12:31.866</queryTime>
        <resultCode>0</resultCode>
        <resultMessage>정상적으로 처리되었습니다.</resultMessage>
    </msgHeader>
    <msgBody>
        <busStationAroundList>
            <centerYn>Y</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22009</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000009</stationId>
            <stationName>신분당선강남역(중)</stationName>
            <x>127.0285667</x>
            <y>37.4957833</y>
            <distance>40</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22653</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000974</stationId>
            <stationName>강남역우리은행</stationName>
            <x>127.02835</x>
            <y>37.49575</y>
            <distance>46</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>Y</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22010</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000010</stationId>
            <stationName>신분당선강남역(중)</stationName>
            <x>127.02905</x>
            <y>37.49505</y>
            <distance>55</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22997</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000921</stationId>
            <stationName>강남역나라빌딩앞</stationName>
            <x>127.0282833</x>
            <y>37.4958667</y>
            <distance>58</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22600</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000937</stationId>
            <stationName>강남역도시에빛</stationName>
            <x>127.0288</x>
            <y>37.4948167</y>
            <distance>68</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22654</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000973</stationId>
            <stationName>강남역티월드</stationName>
            <x>127.0288667</x>
            <y>37.4947</y>
            <distance>83</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277101696</stationId>
            <stationName>삼성화재서초타워(경유)</stationName>
            <x>127.02895</x>
            <y>37.4945333</y>
            <distance>103</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22652</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000541</stationId>
            <stationName>강남역서초현대타워앞</stationName>
            <x>127.02795</x>
            <y>37.4965667</y>
            <distance>140</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 23572</mobileNo>
            <regionName>서울</regionName>
            <stationId>122900101</stationId>
            <stationName>신분당선.강남역4번출구</stationName>
            <x>127.0296667</x>
            <y>37.4943167</y>
            <distance>153</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22348</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000271</stationId>
            <stationName>강남역하나은행</stationName>
            <x>127.02775</x>
            <y>37.4968833</y>
            <distance>181</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277101612</stationId>
            <stationName>우성아파트앞사거리(경유)</stationName>
            <x>127.0295333</x>
            <y>37.49335</y>
            <distance>243</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22120</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000044</stationId>
            <stationName>서초청소년도서관</stationName>
            <x>127.0289667</x>
            <y>37.4926333</y>
            <distance>313</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 23287</mobileNo>
            <regionName>서울</regionName>
            <stationId>122000184</stationId>
            <stationName>강남역.역삼세무서</stationName>
            <x>127.0293167</x>
            <y>37.4982333</y>
            <distance>318</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22167</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000091</stationId>
            <stationName>강남역.강남역사거리</stationName>
            <x>127.0265333</x>
            <y>37.4978</y>
            <distance>324</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 23396</mobileNo>
            <regionName>서울</regionName>
            <stationId>122000742</stationId>
            <stationName>삼성서초역삼세무서</stationName>
            <x>127.0298167</x>
            <y>37.4983833</y>
            <distance>345</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22905</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000920</stationId>
            <stationName>래미안아파트.파이낸셜뉴스</stationName>
            <x>127.0299667</x>
            <y>37.4924</y>
            <distance>356</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22118</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000042</stationId>
            <stationName>래미안에스티지</stationName>
            <x>127.02595</x>
            <y>37.4930333</y>
            <distance>356</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 23494</mobileNo>
            <regionName>서울</regionName>
            <stationId>122000745</stationId>
            <stationName>강남역12번출구A</stationName>
            <x>127.0293833</x>
            <y>37.4987333</y>
            <distance>370</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 23284</mobileNo>
            <regionName>서울</regionName>
            <stationId>122000181</stationId>
            <stationName>강남역12번출구</stationName>
            <x>127.0294667</x>
            <y>37.4987667</y>
            <distance>375</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277102911</stationId>
            <stationName>강남역10번출구(경유)</stationName>
            <x>127.0269833</x>
            <y>37.4986167</y>
            <distance>384</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22114</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000038</stationId>
            <stationName>롯데칠성</stationName>
            <x>127.0239667</x>
            <y>37.4966833</y>
            <distance>437</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277102178</stationId>
            <stationName>역삼초교교차로(경유)</stationName>
            <x>127.03325</x>
            <y>37.4939167</y>
            <distance>441</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>Y</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22007</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000007</stationId>
            <stationName>래미안아파트.파이낸셜뉴스(중)</stationName>
            <x>127.0305333</x>
            <y>37.4916667</y>
            <distance>450</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277102187</stationId>
            <stationName>국기원입구교차로(경유)</stationName>
            <x>127.0308333</x>
            <y>37.4992</y>
            <distance>462</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>2</districtCd>
            <regionName>서울</regionName>
            <stationId>277101867</stationId>
            <stationName>역삼동.우성아파트(경유)</stationName>
            <x>127.0304167</x>
            <y>37.4914</y>
            <distance>474</distance>
        </busStationAroundList>
        <busStationAroundList>
            <centerYn>N</centerYn>
            <districtCd>1</districtCd>
            <mobileNo> 22165</mobileNo>
            <regionName>서울</regionName>
            <stationId>121000089</stationId>
            <stationName>진흥아파트</stationName>
            <x>127.0237833</x>
            <y>37.4976167</y>
            <distance>494</distance>
        </busStationAroundList>
    </msgBody>
</response>
 */