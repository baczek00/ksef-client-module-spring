package pl.lukaszbaczek.ksefClient.client;

import java.util.List;

public class WebClientKSEFException extends RuntimeException {
    private ExceptionDetails exceptionDetails;

    public WebClientKSEFException(String message) {
        super(message);
    }

    public WebClientKSEFException(String message, ExceptionDetails exceptionDetails) {
        super(message);
        this.exceptionDetails = exceptionDetails;
    }

    public ExceptionDetails getExceptionDetails() {
        return exceptionDetails;
    }

    public static class ExceptionDetails {
        private String serviceCtx;
        private String serviceCode;
        private String serviceName;
        private String timestamp;
        private List<ExceptionDetailList> exceptionDetailList;

        public String getServiceCtx() {
            return serviceCtx;
        }

        public void setServiceCtx(String serviceCtx) {
            this.serviceCtx = serviceCtx;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public List<ExceptionDetailList> getExceptionDetailList() {
            return exceptionDetailList;
        }

        public void setExceptionDetailList(List<ExceptionDetailList> exceptionDetailList) {
            this.exceptionDetailList = exceptionDetailList;
        }

        public static class ExceptionDetailList {
            private int exceptionCode;
            private String exceptionDescription;

            public int getExceptionCode() {
                return exceptionCode;
            }

            public void setExceptionCode(int exceptionCode) {
                this.exceptionCode = exceptionCode;
            }

            public String getExceptionDescription() {
                return exceptionDescription;
            }

            public void setExceptionDescription(String exceptionDescription) {
                this.exceptionDescription = exceptionDescription;
            }
        }
    }

    public static class ExceptionResponse {
        private ExceptionDetails exception;

        public ExceptionDetails getException() {
            return exception;
        }

        public void setException(ExceptionDetails exception) {
            this.exception = exception;
        }
    }
}