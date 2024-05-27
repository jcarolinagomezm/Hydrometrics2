FROM node:20.13.1-alpine3.18 as builder

WORKDIR /app

COPY package*.json .

RUN npm ci --legacy-peer-deps

COPY . .

RUN npm run build

FROM nginx:stable-alpine3.19-otel

COPY --from=builder /app/dist/adminlte /usr/share/nginx/html

EXPOSE 80