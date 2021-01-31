package com.app.dubaiculture.data.repository.explore.local.models

data class Explore(
    var category: String,
    var attractions: List<Attraction>,
    var mustSee: List<MustSee>,
    var upcomingEvents: List<UpComingEvents>,
    var popularServices: List<PopularServices>,
    var latestNews: List<LatestNews>
)
