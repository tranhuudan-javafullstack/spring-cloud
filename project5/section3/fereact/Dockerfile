# Use a Node.js image as the base
FROM node:lts-slim AS build

ARG REACT_APP_KEYCLOAK_URI
ARG REACT_APP_RESOUCE_URI
ARG REACT_APP_KEYCLOAK_CLIENT_ID
ARG REACT_APP_KEYCLOAK_CLIENT_SECRET
ARG REACT_APP_KEYCLOAK_CLIENT_SCOPE
ARG REACT_APP_KEYCLOAK_ADMIN_URI
# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json for faster build
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

ENV REACT_APP_KEYCLOAK_URI $REACT_APP_KEYCLOAK_URI
ENV REACT_APP_RESOUCE_URI $REACT_APP_RESOUCE_URI
ENV REACT_APP_KEYCLOAK_CLIENT_ID $REACT_APP_KEYCLOAK_CLIENT_ID
ENV REACT_APP_KEYCLOAK_CLIENT_SECRET $REACT_APP_KEYCLOAK_CLIENT_SECRET
ENV REACT_APP_KEYCLOAK_CLIENT_SCOPE $REACT_APP_KEYCLOAK_CLIENT_SCOPE
ENV REACT_APP_KEYCLOAK_ADMIN_URI $REACT_APP_KEYCLOAK_ADMIN_URI

# Build the React application
RUN npm run build

# Copy only the build output to a new stage
FROM nginx:alpine-slim

COPY --from=build /app/build /usr/share/nginx/html

# Cấu hình Nginx tối ưu
COPY /nginx/default.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
