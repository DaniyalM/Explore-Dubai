package com.app.dubaiculture.data.repository.explore.local.models

data class TestItem(val id: Int, val description: String, val type: Int, val title: String, val number: Int, val code: Int
                    , val attractions : List<Attraction>
                    , val upComingEvents: List<UpComingEvents>
                    , val mustsees: List<MustSee>
                    , val popularService: List<PopularServices>,
                    val latestNews: List<LatestNews>

)